package com.example.android.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.android.Database.RecipeRepository;
import com.example.android.Recipe;


public class AddViewModel extends AndroidViewModel {

    private final MutableLiveData<Bitmap> imageBitmap = new MutableLiveData<>();

    private final RecipeRepository repository;

    public AddViewModel(@NonNull Application application) {
        super(application);
        repository = new RecipeRepository(application);
    }

    public void setImageBitmap(Bitmap bitmap) {
        imageBitmap.setValue(bitmap);
    }

    public MutableLiveData<Bitmap> getImageBitmap() {
        return imageBitmap;
    }

    public void insertRecipe(Recipe recipe){ repository.addRecipe(recipe); }
}