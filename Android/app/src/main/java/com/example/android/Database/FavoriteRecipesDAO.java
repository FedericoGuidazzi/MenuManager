package com.example.android.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.FavoriteRecipes;

import java.util.List;

@Dao
public interface FavoriteRecipesDAO {

    @Insert
    void insertFavoriteRecipe(FavoriteRecipes favoriteRecipes);

    @Query("Select * from FavoriteRecipes where userId =:userId")
    List<FavoriteRecipes> getFavoriteRecipes(int userId);

    @Query("Select * from FavoriteRecipes where userId =:userId and recipeId =:recipeId")
    FavoriteRecipes getFavoriteRecipe(int userId, int recipeId);

    @Query("Delete from FavoriteRecipes where userId =:userId and recipeId =:recipeId")
    void removeFavoriteRecipe(int userId, int recipeId);
}
