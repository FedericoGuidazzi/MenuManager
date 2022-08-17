package com.example.android;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    public int id;

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

    public Recipe(String title, String description, String ingredients, int author, String photo, String guidelines) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.author = author;
        this.photo = photo;
        this.guidelines = guidelines;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getAuthor() {
        return author;
    }

    public String getPhoto(){
        return photo;
    }

    public String getGuidelines(){ return guidelines;}

}
