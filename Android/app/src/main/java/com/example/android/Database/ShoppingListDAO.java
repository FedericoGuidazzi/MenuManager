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

    @Query("Select * from ItemShoppingList where id =:id")
    ItemShoppingList getItem(int id);

    @Query("Delete from ItemShoppingList where id =:id")
    void deleteItem(int id);

    @Query("Update ItemShoppingList set name =:name , quantity=:quantity where id=:id")
    void updateItem(String name, int quantity, int id);

    //fare query update e remove dell'item del bro

}
