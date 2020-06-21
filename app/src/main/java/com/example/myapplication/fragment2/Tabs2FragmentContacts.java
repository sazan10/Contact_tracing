package com.example.myapplication.fragment2;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.adapters.ContactsListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.fragment.app.ListFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tabs2FragmentContacts extends ListFragment {

    OkHttpClient client = new OkHttpClient();
    ContactsListViewAdapter adapter;
//    String server_ip = getActivity().getString(R.string.server_ip);

    //    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new ContactsListViewAdapter() ;
        setListAdapter(adapter);
//        final TextView death = statView.findViewById(R.id.death);
//        final TextView new_case = statView.findViewById(R.id.today_newcase);

        Request request = new Request.Builder()
//                .url("http://192.168.43.192:3000/rss/")
                .url("http://192.168.0.33:3000/mohp/health-facility/hospital")

//                .url("https://reqres.in/api/users?page=2")
                .build();
        Response response = null;
//adapter.spinner.setVisibility(View.VISIBLE);
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
                    JSONArray reader = null;

                    try {
                        reader = new JSONArray(myResponse);
                        for (int i = 0; i < reader.length(); i++) {
                            JSONObject all = reader.getJSONObject(i);
                            final String hospitalStr = all.getString("name");
                            final String provinceStr = all.getString("province");
                            final String districtStr = all.getString("district");
                            final String municipalityStr = all.getString("municipality");
                            final String contactStr = all.getString("contact_number");
                            adapter.addItem(hospitalStr,provinceStr,districtStr,municipalityStr, contactStr);
                        }



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

