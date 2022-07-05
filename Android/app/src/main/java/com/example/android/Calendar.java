package com.example.android;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Calendar {

    @PrimaryKey(autoGenerate = true)
    public int id;
    
    @ColumnInfo(name="date")
    public Date date;

    @ColumnInfo(name="breakfast")
    public String breakfast;

    @ColumnInfo(name="lunch")
    public String lunch;

    @ColumnInfo(name="dinner")
    public String dinner;

    @ColumnInfo(name="user")
    public int user;


}
