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

    @Query("Select * from FavoriteRecipes where userId =:userId and title LIKE '%' || :query  || '%'")
    List<FavoriteRecipes> getFavoriteRecipesForTitle(int userId, String query);

    @Query("Select * from FavoriteRecipes where userId =:userId and recipeId =:recipeId")
    FavoriteRecipes getFavoriteRecipe(int userId, int recipeId);

    @Query("Delete from FavoriteRecipes where userId =:userId and recipeId =:recipeId")
    void removeFavoriteRecipe(int userId, int recipeId);

    @Query("Delete from FavoriteRecipes where recipeId=:recipeId")
    void deleteRecipe(int recipeId);

    @Query("Update FavoriteRecipes set title =:title, description =:description, ingredients =:ingredients, guidelines =:guidelines, photo =:photo where recipeId =:recipeId")
    void uploadRecipe(String title, String description, String ingredients, String guidelines, String photo, int recipeId);
}
