package com.example.myapplication;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.ListFragment;

public class Tabs2FragmentStats extends ListFragment {

    @RequiresApi(api = Build.VERSION_CODES.O)


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        String[] scripts = {"Volvo", "BMW", "Ford", "Mazda"};



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, scripts);
        setListAdapter(adapter);




//            Log.i("rss feed",d);

//        return rootView;

    }
}

