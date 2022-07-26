package com.example.android;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.Database.FavoriteRecipesRepository;
import com.example.android.Database.RecipeRepository;
import com.example.android.Database.userRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InnerRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InnerRecipeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int recipeId;
    private RecipeRepository recipeRepository;
    private FavoriteRecipesRepository favoriteRecipesRepository;
    private userRepository userRepository;
    private TextView recipeNameTextView;
    private TextView recipeAuthorTextView;
    private TextView recipeDescriptionTextView;
    private TextView recipeIngredientsTextView;
    private TextView recipeGuideLinesTextView;
    private ImageView recipeImageView;
    private ImageButton shareButton;
    private ImageButton actionFavourite;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InnerRecipeFragment() {
        // Required empty public constructor
    }

    public InnerRecipeFragment(int recipeId){
        this.recipeId = recipeId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InnerRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InnerRecipeFragment newInstance(String param1, String param2) {
        InnerRecipeFragment fragment = new InnerRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        favoriteRecipesRepository = new FavoriteRecipesRepository(getActivity().getApplication());
        recipeRepository = new RecipeRepository(getActivity().getApplication());
        userRepository = new userRepository(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inner_recipe, container, false);
        Recipe recipe = recipeRepository.getRecipe(recipeId);
        String authorName = userRepository.getUsername(recipe.author);
        recipeNameTextView = view.findViewById(R.id.recipe_name);
        shareButton = view.findViewById(R.id.share_button);
        //fare le cose in caso si voglia fare lo share

        actionFavourite = view.findViewById(R.id.action_favourite);
        //settare la giusta icona per il bottone
        FavoriteRecipes favoriteRecipes = favoriteRecipesRepository.getRecipe(((GlobalClass)getActivity().getApplication()).getUserId(), recipeId);
        if(favoriteRecipes == null){
            actionFavourite.setBackgroundResource(R.drawable.ic_baseline_save_alt_24);
            actionFavourite.setMaxWidth(30);
            actionFavourite.setMaxHeight(30);
        } else {
            actionFavourite.setBackgroundResource(R.drawable.ic_baseline_bookmark_remove_24);
            actionFavourite.setMaxWidth(30);
            actionFavourite.setMaxHeight(30);
        }


        recipeAuthorTextView = view.findViewById(R.id.recipe_author);
        recipeImageView = view.findViewById(R.id.recipe_image);
        recipeDescriptionTextView = view.findViewById(R.id.recipe_description);
        recipeIngredientsTextView = view.findViewById(R.id.recipe_ingredients);
        recipeGuideLinesTextView = view.findViewById(R.id.recipe_guidelines);

        recipeAuthorTextView.setText(authorName);
        recipeAuthorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cambiare nella schermata dell'utente
                Log.e("premuto", ""+recipe.author );
            }
        });
        if (recipe.photo.contains("ic_")){
            Drawable drawable = AppCompatResources.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_baseline_fastfood_24);
            recipeImageView.setImageDrawable(drawable);
        } else {
            Bitmap bitmap = Utilities.getImageBitmap(getActivity(), Uri.parse(recipe.photo));
            if (bitmap != null){
                recipeImageView.setImageBitmap(bitmap);
            }
        }
        actionFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionFavourite.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.ic_baseline_save_alt_24).getConstantState())) {
                    //inserire la ricetta nelle preferite
                    FavoriteRecipes favoriteRecipes1 = new FavoriteRecipes(recipe.title, recipe.description,
                            recipe.ingredients, recipe.author, recipe.photo, recipe.guidelines, recipe.id,((GlobalClass)getActivity().getApplication()).getUserId());
                            userRepository.updateUserScore(100, recipe.author);
                    favoriteRecipesRepository.insertFavoriteRecipe(favoriteRecipes1);
                    actionFavourite.setBackgroundResource(R.drawable.ic_baseline_bookmark_remove_24);
                } else {
                   //rimuovere la ricetta dalle preferite
                    favoriteRecipesRepository.removeFavoriteRecipe(((GlobalClass)getActivity().getApplication()).getUserId(), recipeId);
                    userRepository.updateUserScore(-100, recipe.author);
                    actionFavourite.setBackgroundResource(R.drawable.ic_baseline_save_alt_24);
                }


            }
        });

        recipeNameTextView.setText(recipe.title);
        recipeDescriptionTextView.setText(recipe.description);
        recipeIngredientsTextView.setText(recipe.ingredients);
        recipeGuideLinesTextView.setText(recipe.guidelines);
        return view;
    }
}