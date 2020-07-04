package com.example.myapplication.navBarActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.classes.LocationClass;
import com.example.myapplication.database.DatabaseHandler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.zip.ZipEntry;

import androidx.appcompat.app.AppCompatActivity;

public class VisitedLocationActivity extends AppCompatActivity {
    ListView mainListView ;
    DatabaseHandler db;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.visited_location);
        db = new DatabaseHandler(getApplicationContext());
        List<LocationClass> location1List;
        list = findViewById(R.id.mobile_list);
        location1List = db.getAllLocationClasss();
        int size = location1List.size();
        String[] scripts = new String [size];

        long time;
        LocalDateTime date = null;
        Instant instant;
        if(location1List.size()>0) {
            for (int i = 0; i < location1List.size(); i++) {
                time = Long.valueOf(location1List.get(i).getTime());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    instant = Instant.ofEpochMilli(time);
                    date = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

                }
                scripts[i] = "Lat: " + location1List.get(i).getLatitude().toString() + "  Long: " + location1List.get(i).getLongitude().toString() + "  time: " + date.toString();


            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, scripts);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
