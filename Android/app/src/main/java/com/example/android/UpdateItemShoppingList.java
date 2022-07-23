package com.example.android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.Database.ShoppingListRepository;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateItemShoppingList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateItemShoppingList extends Fragment {

    private String itemId;
    private EditText itemName;
    private EditText itemQuantity;
    private MaterialButton updateButton;
    private MaterialButton removeButton;
    private ShoppingListRepository shoppingListRepository;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateItemShoppingList() {
        // Required empty public constructor
    }

    public UpdateItemShoppingList(String id) {
        this.itemId = id;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateItemShoppingList.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateItemShoppingList newInstance(String param1, String param2) {
        UpdateItemShoppingList fragment = new UpdateItemShoppingList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        shoppingListRepository = new ShoppingListRepository(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_item_shopping_list, container, false);
        itemName = view.findViewById(R.id.item_name_update);
        itemQuantity = view.findViewById(R.id.item_quantity_update);
        updateButton = view.findViewById(R.id.button_update_item);
        removeButton = view.findViewById(R.id.button_remove_item);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemName.getText().toString().equals("") || itemQuantity.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "You must insert datas in all of the EditText", Toast.LENGTH_SHORT).show();
                } else if(Integer.parseInt(itemQuantity.getText().toString()) <= 0){
                    Toast.makeText(getActivity(), "You must insert a greater quantity", Toast.LENGTH_SHORT).show();
                } else {
                    shoppingListRepository.updateItem(itemName.getText().toString(), Integer.parseInt(itemQuantity.getText().toString()), Integer.parseInt(itemId));
                    ((MainActivity)getActivity()).replaceFragment(new ShoppingListFragment());
                }
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingListRepository.removeItem(Integer.parseInt(itemId));
                ((MainActivity)getActivity()).replaceFragment(new ShoppingListFragment());
            }
        });
        ItemShoppingList item = shoppingListRepository.getItem(Integer.parseInt(this.itemId));
        if(item != null){
            itemName.setText(item.getName());
            itemQuantity.setText(""+item.getQuantity());
        }
        return view;
    }
}