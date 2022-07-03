package com.example.android.RecycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;

public class RecipeviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView recipeNameTextView;
    ImageView recipeImageImageView;

    private final OnItemListener itemListener;

    public RecipeviewHolder(@NonNull View itemView, OnItemListener listener){
        super(itemView);

        recipeImageImageView = itemView.findViewById(R.id.recipe_image);
        recipeNameTextView = itemView.findViewById(R.id.recipe_title);

        itemListener = listener;

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }
}

