package com.example.android.RecycleView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.FavoriteRecipes;
import com.example.android.InnerRecipeFragment;
import com.example.android.ItemShoppingList;
import com.example.android.MainActivity;
import com.example.android.R;
import com.example.android.Recipe;
import com.example.android.UpdateItemShoppingList;
import com.example.android.Utilities;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeSocialAdapter extends RecyclerView.Adapter<RecipeSocialAdapter.MyViewHolder>{

    private ArrayList<Recipe> itemList;
    private Fragment fragment;

    public RecipeSocialAdapter(ArrayList<Recipe> list, Fragment fragment){
        this.itemList = list;
        this.fragment = fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView recipeName;
        private TextView recipeId;
        private ImageView imageView;
        private MaterialCardView cardView;

        public MyViewHolder(final View view){
            super(view);
            cardView = view.findViewById(R.id.recipe_card);
            recipeName = view.findViewById(R.id.recipe_title);
            recipeId = view.findViewById(R.id.id_text_view);
            imageView = view.findViewById(R.id.recipe_image);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)fragment.getActivity()).replaceFragment(new InnerRecipeFragment(Integer.parseInt(recipeId.getText().toString())));
                }
            });

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout, parent, false);
        return new RecipeSocialAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = itemList.get(position).getTitle();
        int id = itemList.get(position).getId();
        String photo = itemList.get(position).getPhoto();
        holder.recipeName.setText(name);
        holder.recipeId.setText(""+id);
        holder.imageView.setImageURI(Uri.parse(photo));
        if (photo.contains("ic_")){
            Drawable drawable = AppCompatResources.getDrawable(fragment.getActivity().getApplicationContext(), R.drawable.ic_baseline_fastfood_24);
            holder.imageView.setImageDrawable(drawable);
        } else {
            Bitmap bitmap = Utilities.getImageBitmap(fragment.getActivity(), Uri.parse(photo));
            if (bitmap != null){
                //holder.recipeImageImageView.setImageURI(Uri.parse(imagePath));
                holder.imageView.setImageBitmap(bitmap);
            }
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
