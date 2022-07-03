package com.example.android;

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

    @ColumnInfo(name="numberSaved")
    public int numberSaved;

    @ColumnInfo(name="author")
    public int author;
}
