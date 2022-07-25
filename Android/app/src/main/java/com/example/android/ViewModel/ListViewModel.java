package com.example.android.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.Database.RecipeRepository;
import com.example.android.Recipe;

import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private final MutableLiveData<Recipe> itemSelected = new MutableLiveData<>();

    public List<Recipe> recipes;

    public ListViewModel(@NonNull Application application) {
        super(application);
        RecipeRepository repository = new RecipeRepository(application);
        recipes = repository.getRecipes();
    }

    public List<Recipe> getRecipeItems() {
        return recipes;
    }

    public MutableLiveData<Recipe> getRecipeSelected() {
        return itemSelected;
    }

    public void setItemSelected(Recipe recipe) {
        itemSelected.setValue(recipe);
    }

}
