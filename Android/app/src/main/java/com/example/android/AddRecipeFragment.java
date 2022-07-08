package com.example.android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.Database.RecipeRepository;
import com.google.android.material.button.MaterialButton;

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
    RecipeRepository recipeRepository;
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(title.getText().toString().equals("") || title.getText().toString().equals("Title") ||
                        description.getText().toString().equals("") || description.getText().toString().equals("Description") ||
                        ingredients.getText().toString().equals("") || ingredients.getText().toString().equals("Ingredients") ||
                        guidelines.getText().toString().equals("") || guidelines.getText().toString().equals("Guidelines"))){
                    //inserire la ricetta controllando se lo user ha inserito la foto o no
                } else {
                    Toast.makeText(getActivity(), "You need to fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}