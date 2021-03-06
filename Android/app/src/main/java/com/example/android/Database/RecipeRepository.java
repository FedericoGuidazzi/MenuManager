package com.example.android.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.android.Recipe;

import java.util.List;

public class RecipeRepository {
    private final RecipeDAO recipeDAO;

    private final LiveData<List<Recipe>> recipeList;

    public RecipeRepository(Application application){
        database db = database.getDatabase(application);
        recipeDAO = db.recipeDAO();
        recipeList = recipeDAO.getRecipes();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeList;
    }

    public void addRecipe(Recipe recipe){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDAO.insertRecipe(recipe);
            }
        });
    }
}
