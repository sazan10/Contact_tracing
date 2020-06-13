package com.example.myapplication;


import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tabs2FragmentStats extends ListFragment {

    OkHttpClient client = new OkHttpClient();
    StatsListViewAdapter adapter;
//    String server_ip = getContext().getString(R.string.server_ip);

//    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new StatsListViewAdapter() ;
        setListAdapter(adapter);
//        final TextView death = statView.findViewById(R.id.death);
//        final TextView new_case = statView.findViewById(R.id.today_newcase);

                Request request = new Request.Builder()
//                .url("http://192.168.43.192:3000/rss/")
                .url("http://192.168.10.20:3000/mohp/")

//                .url("https://reqres.in/api/users?page=2")
                .build();
        Response response = null;

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("response",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    final String myResponse = response.body().string();
//                    return myResponse;
                    Log.i("Response",myResponse);
                    JSONObject reader = null;

                    try {
                        reader = new JSONObject(myResponse);
                        final String newDeathStr = reader.getString("today_death");
                        final String recoveredStr = reader.getString("recovered");
                        final String recoveredTodayStr = reader.getString("today_recovered");
                        final String testedStr = reader.getString("rdt_test");

                        final String positiveStr = reader.getString("positive");
                        final String newCaseStr = reader.getString("today_newcase");
                        String [] statElements = {"POSITIVE", "NEW CASES", "NEW DEATHS", "RECOVERED", "NEW RECOVERY", "TESTED"};
                        adapter.addItem(statElements[0], positiveStr, statElements[1], newCaseStr) ;
                        adapter.addItem(statElements[2], newDeathStr, statElements[3], recoveredStr) ;
                        adapter.addItem(statElements[4], recoveredTodayStr, statElements[5], testedStr) ;


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                                death.setText(deathStr);
//                                new_case.setText(newCaseStr);
                                adapter.notifyDataSetChanged();



                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}

