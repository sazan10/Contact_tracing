package com.example.myapplication.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;



import com.example.myapplication.Navigation.NavigationDrawerActivity;
import com.example.myapplication.R;
import com.example.myapplication.navBarActivity.MapsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {



    OkHttpClient client = new OkHttpClient();

    ImageView imageView;
    //EditText username;
    EditText password;
    EditText confirm;
    EditText phone_number;
    TextView switcher;
    Button sign;
    SharedPreferences sharedpreferences ;
    JSONObject user_detail = new JSONObject();
    Toolbar toolbar;
    String access_type;
    //private String urlString = "http://192.168.1.119:3001";
    boolean connection_flag = false;
    SharedPreferences shared;

    //    String url = "http://192.168.1.119:3000/android/";
     private final String url = "https://covidnasdjagno.herokuapp.com/";
     private final String login = "api/user/login/";
     private final String register = "api/user/register/";
   // private final String url = "http://drone.nicnepal.org:8081/android/";


    Spinner username;
    String user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);


        //username.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
     //   username.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

// Request a string response from the provided URL.

        phone_number= (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        confirm = (EditText) findViewById(R.id.confirm);
        sign = (Button) findViewById(R.id.button);
        switcher = findViewById(R.id.switcher);
        sign.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (sign.getText().toString().toUpperCase().equals("SIGN UP"
                )
                ) {

                    signUpRequest();
                    Toast.makeText(getApplicationContext(),"button clicked", Toast.LENGTH_LONG).show();

                }
                else{
                    signInRequest();
                }
//                Intent i = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
//                startActivity(i);

            }
        }
        );
//        confirm.setVisibility(View.VISIBLE);
    }

    public void LogInNow(View view) {
        try {

//            postRequestVolley(queue);

        } catch (Exception e) {
            Log.i("error", e.toString());
        }

        //socket.emit("password",password.getText().toString());
    }


//    public void signUpSignInOption() {
//
//    }
    public void onClickSwitcher(View v) {
        if(switcher.getText().equals("Sign Up"))
        {
            confirm.setVisibility(View.VISIBLE);
            sign.setText("Sign Up");
            switcher.setText("Sign In");
        }
        else

        {
            confirm.setVisibility(View.GONE);
            sign.setText("Sign In");

            switcher.setText("Sign Up");
        }
    }

    public void onDestroy() {

        super.onDestroy();

    }


    public void signInRequest(){
    Toast.makeText(getApplicationContext(),"login",Toast.LENGTH_LONG).show();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", String.valueOf(phone_number.getText()))
                .addFormDataPart("password", String.valueOf(password.getText()))
                .build();
        Request request = new Request.Builder()
//                .url("http://192.168.43.192:3000/rss/")
//                .url("http://192.168.0.33:3000/mohp/")
                .url(url+login)
                .post(requestBody)
//                .url("https://reqres.in/api/users?page=2")
                .build();
        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            Log.i("backend response", response.toString());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Logged In successfully", Toast.LENGTH_SHORT).show();
                        }});


                    JSONObject reader = null;
                    try {
                        reader = new JSONObject(myResponse);
                        Log.i("signin response", reader.toString());
                        final String tokenStr = reader.getString("token");
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("token", String.valueOf(tokenStr));
                        editor.apply();
                        finish();
                        Intent i = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                        startActivity(i);
                    } catch (JSONException e) {
                        Log.i("error", e.toString());
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    public void signUpRequest(){
        Toast.makeText(getApplicationContext(), "button clicked 2", Toast.LENGTH_SHORT).show();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("phone_number", String.valueOf(phone_number.getText()))
                .addFormDataPart("email","sa@gmail.com")
                .addFormDataPart("password", String.valueOf(password.getText()))
                .addFormDataPart("password2", String.valueOf(password.getText()))
                .build();
        Request request = new Request.Builder()
//                .url("http://192.168.43.192:3000/rss/")
//                .url("http://192.168.0.33:3000/mohp/")
                .url(url+register)
                .post(requestBody)
//                .url("https://reqres.in/api/users?page=2")
                .build();
        Response response = null;

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("Error Response Backend",e.toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    final String myResponse = response.body().string();
//                    return myResponse;
                    Log.i("Succes Response Backend",myResponse);
                    JSONObject reader = null;

                    try {
                        reader = new JSONObject(myResponse);
                        Log.i("signup response", reader.toString());
                        final JSONObject finalReader = reader;
                        final String tokenStr = reader.getString("token");
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("token", String.valueOf(tokenStr));
                        editor.apply();
                        finish();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Signed up successfully", Toast.LENGTH_LONG).show();
                            }
                        });

                        Intent i = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                        startActivity(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

//    public void postRequestVolley(RequestQueue queue) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//
//
//                            Log.i("resonse", response.toString());
//                            if (response.toString().equals("OK")) {
//                                signUpSignInOption();
//                                Toast.makeText(getApplicationContext(),"SignIn successful",Toast.LENGTH_SHORT).show();
//                                // Display the first 500 characters of the response string.
//                            } else {
//                                Log.i("info","username or password invalid");
//                                Toast.makeText(getApplicationContext(), "Username or Password is not valid!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        catch (Exception e)
//                        {
//                            Toast.makeText(getApplicationContext(), "Username or Password is not valid!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("info", "That didn't work!"+ error.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> User = new HashMap<String, String>();
//                User.put("username", user_name);
//                //Toast.makeText(getApplicationContext(),user_name,Toast.LENGTH_SHORT).show();
//                Log.i("user_name",user_name);
//                User.put("password", password.getText().toString());
//                return User;
//            }
//        };
//
//        queue.add(stringRequest);
//// Add the request to the RequestQueue.
//
//    }




}

