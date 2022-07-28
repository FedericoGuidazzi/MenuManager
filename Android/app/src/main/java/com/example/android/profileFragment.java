package com.example.android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.Database.RecipeRepository;
import com.example.android.Database.userRepository;
import com.example.android.RecycleView.RecipeSocialAdapter;
import com.example.android.ViewModel.AddViewModel;
import com.google.android.material.button.MaterialButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profileFragment extends Fragment {

    TextView usernameTextView, rankText;
    userRepository userRepository;
    private MaterialButton buttonTake, buttonUpload;
    private ArrayList<Recipe> recipeList;
    private int userId;
    private ImageView profileImage;
    private RecyclerView recyclerView;
    private RecipeRepository recipeRepository;

    public final static int RESULT_LOAD_IMAGE = 2;
    public final static int REQUEST_IMAGE_CAPTURE = 3;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profileFragment() {
        // Required empty public constructor
    }

    public profileFragment(int userId){
        this.userId = userId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new userRepository(getActivity().getApplication());
        recipeRepository = new RecipeRepository(getActivity().getApplication());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        usernameTextView = view.findViewById(R.id.usernameText);
        profileImage = view.findViewById(R.id.profileImage);
        rankText = view.findViewById(R.id.rankText);
        buttonTake = view.findViewById(R.id.button_take);
        buttonUpload = view.findViewById(R.id.button_upload);
        recyclerView = view.findViewById(R.id.recycler_view_profile);
        User user = userRepository.getUser(userId);
        usernameTextView.setText(user.username);
        setRecipes();
        if(userId != ((GlobalClass)getActivity().getApplication()).getUserId()){
            buttonTake.setVisibility(View.GONE);
            buttonUpload.setVisibility(View.GONE);
        }
        if (user.profileImage.contains("ic_")){
            Drawable drawable = AppCompatResources.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_baseline_account_circle_24);
            profileImage.setImageDrawable(drawable);
        } else {
            Bitmap bitmap = Utilities.getImageBitmap(getActivity(), Uri.parse(user.profileImage));
            if (bitmap != null){
                profileImage.setImageBitmap(bitmap);
            }
        }
        List<User> listUserRank = userRepository.getUserByRank();
        int userRank = 0;
        for(int i=1; i<=listUserRank.size();i++){
            if(userId == listUserRank.get(i-1).id){
                userRank = i;
                break;
            }
        }
        rankText.setText(""+userRank+" out of "+listUserRank.size());

        AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(AddViewModel.class);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        buttonTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    getActivity().startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        addViewModel.getImageBitmap().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            //code to visualize image if is taken
            @Override
            public void onChanged(Bitmap bitmap) {
                profileImage.setImageBitmap(bitmap);
                updateImage(addViewModel);
            }
        });

        addViewModel.getImageUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            //code to visualize image if is uploaded
            @Override
            public void onChanged(Uri uri) {
                profileImage.setImageURI(uri);
                updateImage(addViewModel);
            }
        });
        return view;
    }
    void updateImage(AddViewModel addViewModel){
        Bitmap bitmap = addViewModel.getImageBitmap().getValue();
        Uri uri = addViewModel.getImageUri().getValue();
        String image = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +"ic_baseline_profile_circle_24.xml").toString();
        if(bitmap != null){
            try {
                image = String.valueOf(saveImage(bitmap, getActivity()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if(uri != null){
            image = String.valueOf(uri);
        }
        userRepository.updateImage(image, userId);
    }
    private Uri saveImage(Bitmap bitmap, Activity activity) throws FileNotFoundException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY)
                .format(new Date());
        String name = "JPEG_" + timestamp + ".jpg";

        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");

        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues);


        OutputStream outputStream = contentResolver.openOutputStream(imageUri);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageUri;
    }

    private void setRecipes(){
        recipeList = new ArrayList<>(recipeRepository.getUserRecipe(userId));
        RecipeSocialAdapter recyclerAdapter = new RecipeSocialAdapter(recipeList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
    }
}
