package com.example.android.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.ItemShoppingList;

import java.util.List;

@Dao
public interface ShoppingListDAO {

    @Insert
    void insertItem(ItemShoppingList item);

    @Query("Select * from ItemShoppingList where userId =:userid")
    List<ItemShoppingList> getUserItemShoppingList(int userid);

}
