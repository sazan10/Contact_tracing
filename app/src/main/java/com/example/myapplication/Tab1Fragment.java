package com.example.myapplication;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

public class Tab1Fragment extends ListFragment {
    ListView mainListView ;
    DatabaseHandler db;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        ArrayList<String> LocationArray = new ArrayList<String>();
        db = new DatabaseHandler(getActivity());
         List<LocationClass> location1List;
        location1List = db.getAllLocationClasss();
        int size = location1List.size();
        String[] scripts = new String [size];

        long time;
        LocalDateTime date;
        Instant instant;
        if(location1List.size()>0) {
            for (int i = 0; i < location1List.size(); i++) {
                time = Long.valueOf(location1List.get(i).getTime());
                instant = Instant.ofEpochMilli(time);
                date = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                scripts[i] = "Lat: " + location1List.get(i).getLatitude().toString() + "  Long: " + location1List.get(i).getLongitude().toString() + "  time: " + date.toString();

            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, scripts);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
    }

}