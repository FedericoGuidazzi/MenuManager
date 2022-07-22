package com.example.android;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ItemShoppingList {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="quantity")
    private int quantity;

    @ColumnInfo(name="userId")
    private int userId;

    public ItemShoppingList(String name, int quantity, int userId){
        this.name = name;
        this.quantity = quantity;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
