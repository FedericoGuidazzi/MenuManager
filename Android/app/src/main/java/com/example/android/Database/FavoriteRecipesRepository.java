package com.example.android.Database;

import android.app.Application;

import com.example.android.FavoriteRecipes;

import java.util.List;

public class FavoriteRecipesRepository {
    private final FavoriteRecipesDAO favoriteRecipesDAO;

    public FavoriteRecipesRepository(Application application){
        database db = database.getDatabase(application);
        favoriteRecipesDAO = db.favoriteRecipesDAO();
    }

    public List<FavoriteRecipes> getFavoriteRecipes(int userId){
        return favoriteRecipesDAO.getFavoriteRecipes(userId);
    }

    public void insertFavoriteRecipe(FavoriteRecipes favoriteRecipes){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                favoriteRecipesDAO.insertFavoriteRecipe(favoriteRecipes);
            }
        });
    }

    public FavoriteRecipes getRecipe(int userId, int recipeId){
        return favoriteRecipesDAO.getFavoriteRecipe(userId, recipeId);
    }

    public void removeFavoriteRecipe(int userId, int recipeId){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                favoriteRecipesDAO.removeFavoriteRecipe(userId, recipeId);
            }
        });
    }

    public void deleteRecipe(int recipeId){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                favoriteRecipesDAO.deleteRecipe(recipeId);
            }
        });
    }
}
