package com.example.android;

import static java.lang.Integer.*;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.Database.ShoppingListRepository;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddItemShoppingList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemShoppingList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MaterialButton saveButton;

    private EditText itemName;
    private EditText itemQuantity;

    private ShoppingListRepository shoppingListRepository;

    public AddItemShoppingList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemShoppingList.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemShoppingList newInstance(String param1, String param2) {
        AddItemShoppingList fragment = new AddItemShoppingList();
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
        View view = inflater.inflate(R.layout.fragment_add_item_shopping_list, container, false);
        itemName = view.findViewById(R.id.item_name);
        itemQuantity = view.findViewById(R.id.item_quantity);
        saveButton = view.findViewById(R.id.button_save_add_item);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(itemName.getText().toString().equals("") || itemQuantity.getText().toString().equals("")){
                   Toast.makeText(getActivity(), "You must insert something", Toast.LENGTH_SHORT).show();
               } else if(!(parseInt(itemQuantity.getText().toString()) > 0)){
                   Toast.makeText(getActivity(), "You must insert a greater quantity", Toast.LENGTH_SHORT).show();
               } else {
                   shoppingListRepository.insertItemShoppingList(new ItemShoppingList(itemName.getText().toString(), parseInt(itemQuantity.getText().toString()), ((GlobalClass)getActivity().getApplication()).getUserId()));
                   ((MainActivity)getActivity()).replaceFragment(new ShoppingListFragment());
               }
            }
        });
        return view;
    }
}