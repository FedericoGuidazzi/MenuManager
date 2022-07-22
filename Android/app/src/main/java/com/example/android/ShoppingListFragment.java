package com.example.android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.Database.ShoppingListRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton floatingActionButton;
    private ArrayList<ItemShoppingList> itemList;
    private RecyclerView recyclerView;
    private ShoppingListRepository repository;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingListFragment newInstance(String param1, String param2) {
        ShoppingListFragment fragment = new ShoppingListFragment();
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
        itemList = new ArrayList<>();
        repository = new ShoppingListRepository(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        floatingActionButton = view.findViewById(R.id.fab_add);
        recyclerView = view.findViewById(R.id.recycler_view_shopping);
        setItemList();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(new AddItemShoppingList());
            }
        });
        return view;
    }

    private void setItemList(){
        /*LiveData<List<ItemShoppingList>> list = repository.getUserItemShoppingList(((GlobalClass)getActivity().getApplication()).getUserId());
        list.observe(getViewLifecycleOwner(), new Observer<List<ItemShoppingList>>() {
            @Override
            public void onChanged(List<ItemShoppingList> itemShoppingLists) {
                itemList = new ArrayList<>();
                for(ItemShoppingList item : itemShoppingLists){
                    itemList.add(item);
                }
            }
        });*/
        //itemList.add(new ItemShoppingList("forchetta", 2, ((GlobalClass)getActivity().getApplication()).getUserId()));
        itemList = new ArrayList<>(repository.getUserItemShoppingList(((GlobalClass)getActivity().getApplication()).getUserId()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(itemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
        }
}