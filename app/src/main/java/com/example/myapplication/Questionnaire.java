package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Questionnaire extends Fragment {
    private ListView myListView1;
    private CustomListAdapter adapter;
    private Button saveButton;

    public Questionnaire() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<RestaurantInfo> foodList = new ArrayList<>();
        foodList.add(new RestaurantInfo("Burger House",false));
        foodList.add(new RestaurantInfo("Pizza Hut",false));
        foodList.add(new RestaurantInfo("KFC",false));
        foodList.add(new RestaurantInfo("Mc Donalds",false));
        foodList.add(new RestaurantInfo("Sandar",false));
        foodList.add(new RestaurantInfo("Swadista",false));
        foodList.add(new RestaurantInfo("Annapurna",false));

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);
        myListView1 = rootView.findViewById(R.id.myListView1);
        adapter = new CustomListAdapter(this.getContext(), foodList);
        myListView1.setAdapter(adapter);

        saveButton = rootView.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO", "save the data");
            }
        });
        return rootView;
    }
}
