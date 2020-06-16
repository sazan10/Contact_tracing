package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.classes.StatsListViewItem;

import java.util.ArrayList;

public class StatsListViewAdapter extends BaseAdapter {
    // Adapter ArrayList
    private ArrayList<StatsListViewItem> listViewItemList = new ArrayList<StatsListViewItem>() ;
//    private ProgressBar spinner;

    // ListViewAdapter
    public StatsListViewAdapter() {

    }


    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "listview_item" Layout
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.stats_view, parent, false);

        }
        // View convertView2 = inflater.inflate(R.layout.listview_item, parent, false);

        // Layout inflate
        TextView titleTextView1 = (TextView) convertView.findViewById(R.id.textTitle1) ;
        TextView titleTextView2 = (TextView) convertView.findViewById(R.id.textTitle2) ;
        TextView titleTextValue1= (TextView) convertView.findViewById(R.id.textValue1) ;
        TextView titleTextValue2 = (TextView) convertView.findViewById(R.id.textValue2) ;
        // spinner = (ProgressBar) convertView2.findViewById(R.id.progressBar1);
        // Data Set(listViewItemList)
        StatsListViewItem listViewItem = listViewItemList.get(position);
        titleTextView1.setText(listViewItem.getTitleStr1());
        titleTextValue1.setText(listViewItem.getValueStr1());
        titleTextView2.setText(listViewItem.getTitleStr2());
        titleTextValue2.setText(listViewItem.getValueStr2());
        //Log.i("size", String.valueOf(listViewItemList.size()));
        // if(listViewItemList.size()>0)
        return convertView;
        // else return convertView2;
    }

    // (position)
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // (position)
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }
    //add item
    public void addItem(String title1, String value1, String title2,String value2) {
        StatsListViewItem item = new StatsListViewItem();
        item.setTitleStr1(title1);
        item.setValueStr1(value1);
        item.setTitleStr2(title2);
        item.setValueStr2(value2);

        listViewItemList.add(item);
    }
}
