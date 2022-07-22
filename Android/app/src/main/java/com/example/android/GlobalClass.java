package com.example.android;

import android.app.Application;

import androidx.fragment.app.Fragment;

public class GlobalClass extends Application {
    private int userId;
    private Fragment fragment;

    public void setUserId(int id){
        this.userId = id;
    }

    public int getUserId(){
        return this.userId;
    }

}
