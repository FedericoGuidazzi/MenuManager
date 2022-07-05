package com.example.android.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User user);

    @Query("SELECT * From User")
    LiveData<List<User>> getUser();

    @Query("UPDATE User set score= score+ :score where id=:userid")
    void updateUserScore(int score, int userid);

}
