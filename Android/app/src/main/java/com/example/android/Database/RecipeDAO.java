package com.example.android.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.android.Recipe;

import java.util.List;


@Dao
public interface RecipeDAO {

    @Insert
    void insertRecipe(Recipe recipe);

    @Query("SELECT * FROM Recipe")
    List<Recipe> getRecipes();

    @Query("Select id from Recipe order by id desc limit 1")
    int newId();

    @Query("Select * from Recipe where id =:recipeId")
    Recipe getRecipe(int recipeId);
}
