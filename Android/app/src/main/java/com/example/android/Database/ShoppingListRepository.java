package com.example.android.Database;

import android.app.Application;
import android.content.ClipData;

import androidx.lifecycle.LiveData;

import com.example.android.ItemShoppingList;

import java.util.List;

public class ShoppingListRepository {
    private final ShoppingListDAO shoppingListDAO;

    public ShoppingListRepository(Application application){
        database db = database.getDatabase(application);
        shoppingListDAO = db.shoppingListDAO();
    }

    public void insertItemShoppingList(ItemShoppingList item){
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                shoppingListDAO.insertItem(item);
            }
        });
    }

    public List<ItemShoppingList> getUserItemShoppingList(int userId){
        return shoppingListDAO.getUserItemShoppingList(userId);
    }

    public ItemShoppingList getItem(int id){
        return shoppingListDAO.getItem(id);
    }

    public void removeItem(int id){
        shoppingListDAO.deleteItem(id);
    }

    public void updateItem(String name, int quantity, int id){
        shoppingListDAO.updateItem(name, quantity, id);
    }
}
