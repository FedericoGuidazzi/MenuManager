package com.example.android;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="username")
    public String username;

    @ColumnInfo(name="password")
    public String password;

    @ColumnInfo(name="score")
    public int score;

    @ColumnInfo(name="profileImage")
    public String profileImage;
}
