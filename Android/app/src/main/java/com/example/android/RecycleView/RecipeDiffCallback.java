package com.example.android.RecycleView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.android.Recipe;

import java.util.List;

public class RecipeDiffCallback extends DiffUtil.Callback {
    private final List<Recipe> oldCardList;
    private final List<Recipe> newCardList;

    public RecipeDiffCallback(List<Recipe> oldCardList, List<Recipe> newCardList) {
        this.oldCardList = oldCardList;
        this.newCardList = newCardList;
    }

    @Override
    public int getOldListSize() {
        return oldCardList.size();
    }

    @Override
    public int getNewListSize() {
        return newCardList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCardList.get(oldItemPosition) == newCardList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Recipe oldItem = oldCardList.get(oldItemPosition);
        final Recipe newItem = newCardList.get(newItemPosition);
        return oldItem.getTitle().equals(newItem.getTitle()) &&
                oldItem.getDescription().equals(newItem.getDescription()) &&
                oldItem.getIngredients().equals(newItem.getIngredients()) &&
                oldItem.getPhoto().equals(newItem.getPhoto()) &&
                oldItem.getAuthor() == newItem.getAuthor();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
