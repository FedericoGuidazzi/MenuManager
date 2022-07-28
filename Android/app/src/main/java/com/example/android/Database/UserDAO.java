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

    @Query("SELECT * From User where id =:userId")
    User getUser(int userId);

    @Query("Select * from User")
    LiveData<List<User>> getUsers();

    @Query("Select * from User")
    List<User> getUsers1();

    @Query("UPDATE User set score= score+ :score where id=:userid")
    void updateUserScore(int score, int userid);

    @Query("Update User set profileImage =:image where id=:userid")
    void updateProfileImage(String image, int userid);

    @Query("Select username from User where id =:userId")
    String getUsername(int userId);

    @Query("SELECT COUNT(id) FROM User")
    int getUsersCount();

    @Query("Select * from User order by score desc")
    List<User> getUsersByRank();

    @Query("Update User set profileImage =:image where id =:userId")
    void updateImage(String image, int userId);
}
