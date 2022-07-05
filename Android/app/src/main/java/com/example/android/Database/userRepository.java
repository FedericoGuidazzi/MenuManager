package com.example.android.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.android.User;

import java.util.List;

public class userRepository {
    private final UserDAO userDAO;

    private final LiveData<List<User>> userlist;

    public userRepository(Application application){
        database db = database.getDatabase(application);
        userDAO = db.userDAO();
        userlist = userDAO.getUser();
    }

    public LiveData<List<User>> getUser(){
        return userlist;
    }

    public void addUser(User user){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.insertUser(user);
            }
        });
    }

    public void updateUserScore(int score, int userid){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.updateUserScore(score, userid);
            }
        });
    }
}
