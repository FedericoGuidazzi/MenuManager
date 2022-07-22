package com.example.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<ItemShoppingList> itemList;

    public RecyclerAdapter(ArrayList<ItemShoppingList> list){
        this.itemList = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView itemName;
        private TextView itemQuantity;

        public MyViewHolder(final View view){
            super(view);
            itemName = view.findViewById(R.id.item_list_name);
            itemQuantity = view.findViewById(R.id.quantity_item);
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
        holder.itemName.setText(name);
        holder.itemQuantity.setText(""+quantity);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
