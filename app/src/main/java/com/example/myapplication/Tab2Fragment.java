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
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tab2Fragment extends ListFragment {
    ListView mainListView ;
    ListViewAdapter adapter;
    OkHttpClient client = new OkHttpClient();


    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          adapter = new ListViewAdapter() ;
        setListAdapter(adapter);

        //            String d= String.valueOf(doGetRequest("http://192.168.43.192:3000/rss/"));
//        View rootView = inflater.inflate(R.layout.frag2, container, false);
//    final TextView myAwesomeTextView= (TextView) rootView.findViewById(R.id.textView1);
//            String d = example.run("http://192.168.43.192:3000/rss/") ;
        Request request = new Request.Builder()
                .url("http://192.168.254.194:3000/rss/")
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
                        JSONArray items = reader.getJSONArray("items");

                        for (int i=0; i<items.length(); i++) {
                            JSONObject all = items.getJSONObject(i);
                            String title = all.getString("title");
                            String creator = all.getString("creator");
                            String date = all.getString("pubDate");
                            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                            Date date1 = null;
                            String strDate= "n/a";
                            try {
                                date1 = format.parse(date);
                                Log.e("dater ", date1.toString());
                                SimpleDateFormat format2 = new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
                                strDate = format2.format(date1);
                                Log.e("dater ", strDate);

                            } catch (ParseException e) {
                                e.printStackTrace();
                                Log.i("dater error", e.toString());
                            }
//                            String[] arrOfStr = date.split("2020", 2);
//                            Log.i("date from backend", date1.toString());
                            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.news),
                                    title, creator+ " " + strDate) ;


                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            myAwesomeTextView.setText(myResponse);
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                                    android.R.layout., Collections.singletonList(myResponse));
//                            CustomListAdapter adapter = new CustomListAdapter(getActivity().getBaseContext(), myResponse);
//                            setListAdapter(adapter);
                           adapter.notifyDataSetChanged();


                        }
                    });
                }
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



//            Log.i("rss feed",d);

//        return rootView;

    }




    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        // get TextView's Text.
//        ListViewItem item = (ListViewItem) l.getItemAtPosition(position) ;
//
//        String titleStr = item.getTitle() ;
//        String descStr = item.getDesc() ;
//        Drawable iconDrawable = item.getIcon() ;

        // TODO : use item data.
    }
//    String doGetRequest(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }
}

