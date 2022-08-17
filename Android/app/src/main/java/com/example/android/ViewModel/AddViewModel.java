package com.example.android.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.android.Database.RecipeRepository;
import com.example.android.Recipe;


public class AddViewModel extends AndroidViewModel {

    private MutableLiveData<Bitmap> imageBitmap = new MutableLiveData<>();

    private MutableLiveData<Uri> imageUri = new MutableLiveData<>();

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

    public void setImageUri(Uri uri) {
        imageUri.setValue(uri);
    }

    public MutableLiveData<Uri> getImageUri(){ return imageUri;}

    public void clearLiveData(){
        imageBitmap = new MutableLiveData<>();
        imageUri = new MutableLiveData<>();
    }

    public void insertRecipe(Recipe recipe){ repository.addRecipe(recipe); }


}