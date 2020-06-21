package com.example.myapplication.Navigation;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.Questions.Questionnaire;
import com.example.myapplication.mainFragment.ROIFragment;
import com.example.myapplication.mainFragment.Tab1Fragment;
import com.example.myapplication.mainFragment.TabsFrag;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabsFrag tab1 = new TabsFrag();
                return tab1;
            case 1:
                Questionnaire tab2 = new Questionnaire();
                return tab2;
            case 2:
                Tab1Fragment tab3 = new Tab1Fragment();
                return tab3;
            case 3:
                ROIFragment tab4 = new ROIFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

