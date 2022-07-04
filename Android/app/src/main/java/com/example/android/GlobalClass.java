package com.example.android;

import android.app.Application;

public class GlobalClass extends Application {
    private int userId;

    public void setUserId(int id){
        this.userId = id;
    }

    public int getUserId(){
        return this.userId;
    }
}
