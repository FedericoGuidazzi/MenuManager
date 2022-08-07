package com.example.android.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.android.Recipe;

import java.util.List;

public class RecipeRepository {
    private final RecipeDAO recipeDAO;

    private final List<Recipe> recipeList;

    public RecipeRepository(Application application){
        database db = database.getDatabase(application);
        recipeDAO = db.recipeDAO();
        recipeList = recipeDAO.getRecipes();
    }

    public List<Recipe> getRecipes(){
        return recipeList;
    }

    public List<Recipe> getRecipeForTitle(String title){
        return recipeDAO.getRecipeForTitle(title);
    }

    public void addRecipe(Recipe recipe){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDAO.insertRecipe(recipe);
            }
        });
    }

    public int newId(){
        return recipeDAO.newId();
    }

    public Recipe getRecipe(int recipeId){
        return recipeDAO.getRecipe(recipeId);
    }

    public List<Recipe> getUserRecipe(int userId){
        return recipeDAO.getUserRecipe(userId);

    }

    public void deleteRecipe(int recipeId){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDAO.deleteRecipe(recipeId);
            }
        });
    }

    public void uploadRecipe(String title, String description, String ingredients, String guidelines, String photo, int recipeId){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDAO.uploadRecipe(title, description, ingredients, guidelines, photo, recipeId);
            }
        });
    }
}
