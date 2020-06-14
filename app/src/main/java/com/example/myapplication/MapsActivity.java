package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button saveButton;
    final double[] HomeLat1 = new double[1];
    final double[] HomeLong1 = new double[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        saveButton=(Button) findViewById(R.id.button1);
        saveButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("LocationPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("HomeLat", String.valueOf(HomeLat1[0]));
                editor.putString("HomeLong", String.valueOf(HomeLong1[0]));
                editor.apply();
                Log.i("initial distance home", HomeLat1[0] + "   " + HomeLong1[0]);
                finish();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Double HomeLat = 27.689838836059117;
        Double HomeLong = 85.31805333556801;


//            SharedPreferences sharedpreferences = getSharedPreferences("LocationPref", Context.MODE_PRIVATE);
//            HomeLat = Double.valueOf(sharedpreferences.getString("HomeLat", "27.689838836059117"));
//            HomeLong = Double.valueOf(sharedpreferences.getString("HomeLong", "85.31805333556801"));
//


        // Add a marker in Sydney and move the camera
        LatLng home = new LatLng(HomeLat, HomeLong);
        mMap.addMarker(new MarkerOptions().position(home).title("Marker in Kupondole"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home,14));

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Showing / hiding your current location
        googleMap.setMyLocationEnabled(true);

        // Enable / Disable zooming controls
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Enable / Disable my location button
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        googleMap.getUiSettings().setCompassEnabled(false);

        // Enable / Disable Rotate gesture`enter code here`
        googleMap.getUiSettings().setRotateGesturesEnabled(false);

        // Enable / Disable zooming functionality
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(
//                new LatLng(midLatLng.latitude, midLatLng.longitude)));

//        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
//            @Override
//            public void onCameraIdle() {
//                LatLng midLatLng = mMap.getCameraPosition().target;
//                getAddress(midLatLng.latitude, midLatLng.longitude);
//                CameraUpdateFactory.newLatLng(
//                        new LatLng(midLatLng.latitude, midLatLng.longitude));
//            }
//        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
//                allPoints.add(point);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point));
                HomeLat1[0] =point.latitude;
                HomeLong1[0] = point.longitude;
            }
        });



    }
//    public String getAddress(double latitude, double longitude) {
//        StringBuilder result = new StringBuilder();
//        try {
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            if (addresses.size() > 0) {
//                Address address = addresses.get(0);
//
//                for (int i = 0; i <= addresses.get(0).getMaxAddressLineIndex(); i++) {
//                    if (i == addresses.get(0).getMaxAddressLineIndex()) {
//                        result.append(addresses.get(0).getAddressLine(i));
//                    } else {
//                        result.append(addresses.get(0).getAddressLine(i) + ",");
//                    }
//                }
//                Log.i("address" , address.toString());
//               Log.i("adress st" , result.toString());
//
//             // Here is you AutoCompleteTextView where you want to set your string address (You can remove it if you not need it)
//            }
//        } catch (IOException e) {
//            Log.e("tag", e.getMessage());
//        }
//
//        return result.toString();
//    }


}
