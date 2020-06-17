package com.example.myapplication.fragment2;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.myapplication.NewsDetailActivity;
import com.example.myapplication.classes.NewsListViewItem;
import com.example.myapplication.R;
import com.example.myapplication.adapters.NewsListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tabs2FragmentNews extends ListFragment {
    ListView mainListView ;
    NewsListViewAdapter adapter;
    OkHttpClient client = new OkHttpClient();
//    String server_ip = getActivity().getString(R.string.server_ip);


    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new NewsListViewAdapter() ;
        setListAdapter(adapter);

        //            String d= String.valueOf(doGetRequest("http://192.168.43.192:3000/rss/"));
//        View rootView = inflater.inflate(R.layout.frag2, container, false);
//    final TextView myAwesomeTextView= (TextView) rootView.findViewById(R.id.textView1);
//            String d = example.run("http://192.168.43.192:3000/rss/") ;
        Request request = new Request.Builder()
//                .url("http://192.168.43.192:3000/rss/")
                .url("http://192.168.254.3:3000/rss/")

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
                            String link = all.getString("link");
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
                                    title, creator+ " " + strDate,link) ;


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
        NewsListViewItem item = (NewsListViewItem) l.getItemAtPosition(position) ;
//
        String linkStr = item.getLinkStr() ;
        Intent myIntent = new Intent(getActivity(), NewsDetailActivity.class);
        myIntent.putExtra("link", linkStr); //Optional parameters
        this.startActivity(myIntent);
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

