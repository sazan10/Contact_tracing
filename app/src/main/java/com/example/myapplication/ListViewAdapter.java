package com.example.myapplication;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    // Adapter ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
//    private ProgressBar spinner;

    // ListViewAdapter
    public ListViewAdapter() {

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
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

        }
//        View convertView2 = inflater.inflate(R.layout.listview_item, parent, false);

        // Layout inflate
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;
//        spinner = (ProgressBar) convertView2.findViewById(R.id.progressBar1);

        // Data Set(listViewItemList)
        ListViewItem listViewItem = listViewItemList.get(position);


        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());
//Log.i("size", String.valueOf(listViewItemList.size()));
//        if(listViewItemList.size()>0)
            return convertView;
//        else return convertView2;
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
    public void addItem(Drawable icon, String title, String desc,String link) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
    item.setLinkStr(link);
        listViewItemList.add(item);
    }
}