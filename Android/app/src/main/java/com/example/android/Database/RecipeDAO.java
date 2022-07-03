package com.example.android.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.android.Recipe;

import java.util.List;

public interface RecipeDAO {

    @Insert
    void insertRecipe(Recipe recipe);

    @Query("SELECT * From Recipe")
    LiveData<List<Recipe>> getRecipes();
}
