package com.example.android.RecycleView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.example.android.Recipe;
import com.example.android.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeviewHolder> implements Filterable {

    private List<Recipe> recipeList = new ArrayList<>();

    private final Activity activity;

    private final OnItemListener listener;

    private List<Recipe> recipeListNotFiltered = new ArrayList<>();

    public RecipeAdapter(OnItemListener listener, Activity activity){
        this.activity = activity;
        this.listener = listener;
    }


    @Override
    public Filter getFilter() {
        return cardFilter;
    }

    @NonNull
    @Override
    public RecipeviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_layout, parent, false);
        return new RecipeviewHolder(layoutView, listener );
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeviewHolder holder, int position) {
        Recipe currentRecipeItem = recipeList.get(position);
        String imagePath = currentRecipeItem.getPhoto();
        if (imagePath.contains("ic_")){
            Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources()
                    .getIdentifier(imagePath, "drawable", activity.getPackageName()));
            holder.recipeImageImageView.setImageDrawable(drawable);
        } else {
            Bitmap bitmap = Utilities.getImageBitmap(activity, Uri.parse(imagePath));
            if (bitmap != null){
                holder.recipeImageImageView.setImageBitmap(bitmap);
            }
        }

        holder.recipeNameTextView.setText(currentRecipeItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    private final Filter cardFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Recipe> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length()==0){
                filteredList.addAll(recipeListNotFiltered);
            }else{
                String filterPattern = charSequence.toString().toLowerCase(Locale.ROOT).trim();
                for(Recipe recipe : recipeListNotFiltered){
                    if(recipe.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(recipe);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Recipe> filteredList = new ArrayList<>();
            List<?> result = (List<?>) results.values;
            for (Object object : result) {
                if (object instanceof Recipe) {
                    filteredList.add((Recipe) object);
                }
            }

            //warn the adapter that the data are changed after the filtering
            updateCardListItems(filteredList);
        }
    };

    public void updateCardListItems(List<Recipe> filteredList) {
        final RecipeDiffCallback diffCallback =
                new RecipeDiffCallback(this.recipeList, filteredList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.recipeList = new ArrayList<>(filteredList);
        diffResult.dispatchUpdatesTo(this);
    }


    public void setData(List<Recipe> list){
        final RecipeDiffCallback diffCallback =
                new RecipeDiffCallback(this.recipeList, list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.recipeList = new ArrayList<>(list);
        this.recipeListNotFiltered = new ArrayList<>(list);

        diffResult.dispatchUpdatesTo(this);
    }

    public Recipe getItemSelected(int position) {
        return recipeList.get(position);
    }
}
