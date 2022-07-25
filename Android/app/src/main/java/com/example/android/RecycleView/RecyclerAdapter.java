package com.example.android.RecycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.ItemShoppingList;
import com.example.android.MainActivity;
import com.example.android.R;
import com.example.android.UpdateItemShoppingList;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<ItemShoppingList> itemList;
    private Fragment fragment;

    public RecyclerAdapter(ArrayList<ItemShoppingList> list, Fragment fragment){
        this.itemList = list;
        this.fragment = fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView itemName;
        private TextView itemQuantity;
        private TextView itemId;
        private MaterialCardView cardView;

        public MyViewHolder(final View view){
            super(view);
            cardView = view.findViewById(R.id.recipe_card);
            itemName = view.findViewById(R.id.item_list_name);
            itemQuantity = view.findViewById(R.id.quantity_item);
            itemId = view.findViewById(R.id.item_list_id);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)fragment.getActivity()).replaceFragment(new UpdateItemShoppingList(itemId.getText().toString()));
                }
            });

        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String name = itemList.get(position).getName();
        int quantity = itemList.get(position).getQuantity();
        int id = itemList.get(position).getId();
        holder.itemId.setText(""+id);
        holder.itemName.setText(name);
        holder.itemQuantity.setText(""+quantity);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
