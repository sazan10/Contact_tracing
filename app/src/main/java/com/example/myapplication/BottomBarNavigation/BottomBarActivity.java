package com.example.myapplication.BottomBarNavigation;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MapsActivity;
import com.example.myapplication.Questions.Questionnaire;
import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHandler;
import com.example.myapplication.mainFragment.ROIFragment;
import com.example.myapplication.mainFragment.ReportFragment;
import com.example.myapplication.mainFragment.Tab1Fragment;
import com.example.myapplication.service.MyService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class BottomBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    Intent intent;
    Intent mapIntent;
    DatabaseHandler db = new DatabaseHandler(this);
    boolean first_time=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set the layout to activity_nav_drawer which contains the layout of activity_nav_drawer
        //and activity_main. Contentview is set to activity_nav_drawer but we should edit the layout activity_main
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottombar_main);

        SharedPreferences sharedpreferences = getSharedPreferences("LocationPref", Context.MODE_PRIVATE);
        String homeLat = sharedpreferences.getString("HomeLat", null);
        String homeLong = sharedpreferences.getString("HomeLong", null);
        //The app theme is set to noActionBar in styles.xml
        //toolbar is in activity_main.xml
        toolbar = findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);


        //pointed to activity_nav_drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout_1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //if any item is selected in the navigation list, for that a listener is set onNavigationItemSelected(MenuItem item)
        NavigationView navigationView = findViewById(R.id.nav_view_1);
        navigationView.setNavigationItemSelectedListener(this);

        //initializing a bottombar navigation view with the bottombar view  and setting the callback function
        BottomNavigationView navigation = findViewById(R.id.bottom_nav_1);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FloatingActionButton fab = findViewById(R.id.fab_1);

        intent = new Intent(this, MyService.class);
        statusCheck();
        mapIntent = new Intent(this, MapsActivity.class);
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(this,intent);
            } else {
                startService(intent);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(BottomBarActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            ActivityCompat.requestPermissions(BottomBarActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},44);
        }

        if(homeLat==null || homeLong==null)
        {
            startActivity(mapIntent);

        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mapIntent);

            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && first_time) {
            alertToLogout();// do something on back.
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void statusCheck() { //checks if location is enabled
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }private void buildAlertMessageNoGps() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==44 && grantResults.length
                > 0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ContextCompat.startForegroundService(this, intent);
                } else {
                    startService(intent);
                }
            }
            else{
                Toast.makeText(this, "Permission denied",Toast.LENGTH_SHORT).show();
            }
        }

    }

    //callback function for bottombar navigation when items are selected
    //we can build different fragments and call them when items are selected
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_bottombar_recents:
                    toolbar.setTitle("Recents");
                    fragment = new Questionnaire();
                    loadFragment(fragment);
                    return true;
                case R.id.action_bottombar_attendance:

                    // setSupportActionBar(toolbar);
                    toolbar.setTitle("Attendance");
                    fragment = new ROIFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_bottombar_calendar:
                    toolbar.setTitle("Calendar");
                    fragment = new ReportFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_bottombar_bus:
                    toolbar.setTitle("Bus Routes");
                    fragment = new Tab1Fragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }

    };

    //loading the different fragments when items of bottombar nav are selected
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_fragmentholder_1, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present. like MyProfile, Settings etc
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;

        } else if (id==R.id.logout) {


            alertToLogout(); //asking the user again if he/she wants to logout


        }

        return super.onOptionsItemSelected(item);

    }
    public void alertToLogout()
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ParseUser.logOut();
//                        Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
//                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_credits) {

        } else if (id == R.id.nav_logout) {

        }

        //when a item is selected in slide navigation, the drawer closes itself
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}