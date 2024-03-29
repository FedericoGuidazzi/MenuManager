package com.example.android.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.android.Calendar;
import com.example.android.FavoriteRecipes;
import com.example.android.ItemShoppingList;
import com.example.android.Recipe;
import com.example.android.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Recipe.class, Calendar.class, ItemShoppingList.class, FavoriteRecipes.class}, version = 2)

public abstract class database extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract RecipeDAO recipeDAO();
    public abstract CalendarDAO calendarDAO();
    public abstract ShoppingListDAO shoppingListDAO();
    public abstract FavoriteRecipesDAO favoriteRecipesDAO();

    ///Singleton instance to retrieve when the db is needed
    private static volatile database INSTANCE;

    static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static database getDatabase(final Context context){
        if (INSTANCE == null){
            //The synchronized is to prevent multiple instances being created.
            synchronized (database.class) {
                //If the db has not yet been created, the builder creates it.
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), database.class, "MenuManagerDatabase").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
