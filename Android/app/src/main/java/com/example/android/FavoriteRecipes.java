package com.example.android;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteRecipes {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public FavoriteRecipes(String title, String description, String ingredients, int author, String photo, String guidelines, int recipeId, int userId) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.author = author;
        this.photo = photo;
        this.guidelines = guidelines;
        this.recipeId = recipeId;
        this.userId = userId;
    }

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="description")
    public String description;

    @ColumnInfo(name="ingredients")
    public String ingredients;

    @ColumnInfo(name="author")
    public int author;

    @ColumnInfo(name="photo")
    public String photo;

    @ColumnInfo(name="guidelines")
    public String guidelines;

    @ColumnInfo(name = "recipeId")
    public int recipeId;

    @ColumnInfo(name="userId")
    public int userId;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(String guidelines) {
        this.guidelines = guidelines;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
