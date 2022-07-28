package com.example.android.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.android.User;

import java.util.List;

public class userRepository {
    private final UserDAO userDAO;


    public userRepository(Application application){
        database db = database.getDatabase(application);
        userDAO = db.userDAO();
    }

    public User getUser(int userId){
        return userDAO.getUser(userId);
    }

    public int getUserCount(){
        return userDAO.getUsersCount();
    }

    public List<User> getUserByRank(){
        return userDAO.getUsersByRank();
    }

    public List<User> getUsers1(){
        return userDAO.getUsers1();
    }

    public LiveData<List<User>> getUsers(){
        return userDAO.getUsers();
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

    public void updateImage(String image, int userId){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.updateImage(image, userId);
            }
        });
    }

    public void updateProfileImage(String image, int userid){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.updateProfileImage(image, userid);
            }
        });
    }

    public String getUsername(int userId){
        return userDAO.getUsername(userId);
    }
}
