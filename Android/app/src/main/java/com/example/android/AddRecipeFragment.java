package com.example.android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.Database.RecipeRepository;
import com.example.android.Database.userRepository;
import com.example.android.ViewModel.AddViewModel;
import com.google.android.material.button.MaterialButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecipeFragment extends Fragment {

    MaterialButton uploadPhoto;
    MaterialButton takePicture;
    MaterialButton save;
    EditText title;
    EditText description;
    EditText ingredients;
    EditText guidelines;
    String recipeImage;
    RecipeRepository recipeRepository;
    userRepository userRepository;
    ImageView recipeImageView;
    public final static int RESULT_LOAD_IMAGE = 2;
    public final static int REQUEST_IMAGE_CAPTURE = 3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecipeFragment newInstance(String param1, String param2) {
        AddRecipeFragment fragment = new AddRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeRepository = new RecipeRepository(getActivity().getApplication());
        userRepository = new userRepository(getActivity().getApplication());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        uploadPhoto = view.findViewById(R.id.upload_photo);
        takePicture = view.findViewById(R.id.take_photo);
        save = view.findViewById(R.id.save_recipe);
        title = view.findViewById(R.id.recipe_title_text);
        description = view.findViewById(R.id.recipe_description_text);
        ingredients = view.findViewById(R.id.recipe_ingredients_text);
        guidelines = view.findViewById(R.id.recipe_guidelines_text);
        recipeImageView = view.findViewById(R.id.recipe_image_imageView);

       uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    getActivity().startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(AddViewModel.class);

        addViewModel.getImageBitmap().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            //code to visualize image if is taken
            @Override
            public void onChanged(Bitmap bitmap) {
                recipeImageView.setImageBitmap(bitmap);
            }
        });

        addViewModel.getImageUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            //code to visualize image if is uploaded
            @Override
            public void onChanged(Uri uri) {
                recipeImageView.setImageURI(uri);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(title.getText().toString().equals("") || title.getText().toString().equals("Title") ||
                        description.getText().toString().equals("") || description.getText().toString().equals("Description") ||
                        ingredients.getText().toString().equals("") || ingredients.getText().toString().equals("Ingredients") ||
                        guidelines.getText().toString().equals("") || guidelines.getText().toString().equals("Guidelines"))){
                    String recipeTitle = title.getText().toString();
                    String recipeDescription = description.getText().toString();
                    String recipeIngredients = ingredients.getText().toString();
                    String recipreGuidelines = guidelines.getText().toString();

                    Bitmap bitmap = addViewModel.getImageBitmap().getValue();
                    Uri uri = addViewModel.getImageUri().getValue();


                    if(bitmap != null){
                        try {
                            recipeImage = String.valueOf(saveImage(bitmap, getActivity()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else if(uri != null){
                        recipeImage = String.valueOf(uri);
                    } else {
                        recipeImage = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +"ic_baseline_fastfood_24.xml").toString();
                    }
                    Recipe recipe = new Recipe(recipeTitle, recipeDescription, recipeIngredients,
                            ((GlobalClass)getActivity().getApplication()).getUserId(), recipeImage, recipreGuidelines);
                    recipeRepository.addRecipe(recipe);
                    //add points to the recipe author
                    userRepository.updateUserScore(100, ((GlobalClass)getActivity().getApplication()).getUserId());
                    Toast.makeText(getActivity(), "Your recipe has been added successfully", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).replaceFragment(new recipesFragment());
                } else {
                    Toast.makeText(getActivity(), "You need to fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
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
}