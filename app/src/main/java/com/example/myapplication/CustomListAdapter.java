package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by swain on 3/1/18.
 */

public class CustomListAdapter extends ArrayAdapter<RestaurantInfo> {

    private ArrayList<RestaurantInfo> list;
    Context context;
    private ArrayList<RestaurantInfo> resInfo;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        CheckBox checkbox;
    }

    public CustomListAdapter(@NonNull Context context, ArrayList<RestaurantInfo> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
//        Log.i("INFO", list.get(0).isCheck()+"");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // to add a custom view..

        ViewHolder viewHolder;
        viewHolder = new ViewHolder();

        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_layout, parent, false);



            viewHolder.name = convertView.findViewById(R.id.resName);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.check_box);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    list.get(getPosition).setCheck(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                }

            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.check_names, viewHolder.name);
            convertView.setTag(R.id.check_box, viewHolder.checkbox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkbox.setTag(position); // This line is important.

        viewHolder.name.setText(list.get(position).getName());
        viewHolder.checkbox.setChecked(list.get(position).isCheck());

        return convertView;
    }
}
