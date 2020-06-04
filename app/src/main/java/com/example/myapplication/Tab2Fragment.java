package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.io.IOException;
import java.util.Collections;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tab2Fragment extends ListFragment {
    ListView mainListView ;

    OkHttpClient client = new OkHttpClient();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //            String d= String.valueOf(doGetRequest("http://192.168.43.192:3000/rss/"));
//        View rootView = inflater.inflate(R.layout.frag2, container, false);
//    final TextView myAwesomeTextView= (TextView) rootView.findViewById(R.id.textView1);
//            String d = example.run("http://192.168.43.192:3000/rss/") ;
        Request request = new Request.Builder()
                .url("http://192.168.43.192:3000/rss/")
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            myAwesomeTextView.setText(myResponse);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_list_item_1, Collections.singletonList(myResponse));
                            setListAdapter(adapter);
                        }
                    });
                }
            }
        });


//            Log.i("rss feed",d);

//        return rootView;

    }





    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
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

