package com.example.myapplication.mainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragment2.Tabs2FragmentContacts;
import com.example.myapplication.fragment2.Tabs2FragmentInfo;
import com.example.myapplication.fragment2.Tabs2FragmentNews;
import com.example.myapplication.fragment2.Tabs2FragmentStats;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TabsFrag extends Fragment {
    //initializing a bottombar navigation view with the bottombar view  and setting the callback function
    boolean first_time = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.tabs_acitivity, container, false);
        BottomNavigationView navigation = (BottomNavigationView) rootView.findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    return rootView;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_bottombar_news:
                    fragment = new Tabs2FragmentNews();
                    loadFragment(fragment);
                    return true;
                case R.id.action_bottombar_stats:

                    // setSupportActionBar(toolbar);
                    fragment = new Tabs2FragmentStats();
                    loadFragment(fragment);
                    return true;
                case R.id.action_bottombar_info:
                    fragment = new Tabs2FragmentInfo();
                    loadFragment(fragment);
                    return true;
                case R.id.action_bottombar_contacts:
                    fragment = new Tabs2FragmentContacts();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }

    };

   // loading the different fragments when items of bottombar nav are selected
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_fragmentholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}
