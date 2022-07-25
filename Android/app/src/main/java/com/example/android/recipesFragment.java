package com.example.android;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.Database.FavoriteRecipesRepository;
import com.example.android.Database.RecipeRepository;
import com.example.android.RecycleView.OnItemListener;
import com.example.android.RecycleView.RecipeAdapter;
import com.example.android.RecycleView.RecyclerAdapter;
import com.example.android.ViewModel.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recipesFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    private ArrayList<FavoriteRecipes> recipeList;
    private RecyclerView recyclerView;
    private FavoriteRecipesRepository recipeRepository;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public recipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static recipesFragment newInstance(String param1, String param2) {
        recipesFragment fragment = new recipesFragment();
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
        recipeList = new ArrayList<>();
        recipeRepository = new FavoriteRecipesRepository(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        floatingActionButton = view.findViewById(R.id.fab_add);
        recyclerView = view.findViewById(R.id.recycler_view);
        setRecipes();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(new AddRecipeFragment());
            }
        });
        return view;
    }


    private void setRecipes(){
        recipeList = new ArrayList<>(recipeRepository.getFavoriteRecipes(((GlobalClass)getActivity().getApplication()).getUserId()));
        RecipeAdapter recyclerAdapter = new RecipeAdapter(recipeList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
    }
}