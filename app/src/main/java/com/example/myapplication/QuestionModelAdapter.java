package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class QuestionModelAdapter extends BaseAdapter {
    Context context;
    private ArrayList<QuestionModel> list;
    private ArrayList<Boolean> boolCheck;
    private CheckBox checkBox;
    private boolean check = true;

    // View lookup cache
    private static class ViewHolder {
        TextView text;
        CheckBox checkbox;
    }

    public QuestionModelAdapter(@NonNull Context context, ArrayList<QuestionModel> list) {
        this.context = context;
        this.list = list;
        Log.i("checklistAdapter", Boolean.toString(list.get(0).isCheck()));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    QuestionModel getCheckList(int position) {
        return ((QuestionModel) getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.question_model, null);
//            convertView.setBackground(context.getDrawable(R.drawable.list_border));
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.check_names);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.check_box);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    list.get(getPosition).setCheck(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    check = true;
                    for(QuestionModel c: list) {
                        if(!c.isCheck()) {
                            check = false;
                        }
                    }
                    Log.i("INFO",check + "");
                }

            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.check_names, viewHolder.text);
            convertView.setTag(R.id.check_box, viewHolder.checkbox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkbox.setTag(position); // This line is important.

        viewHolder.text.setText(list.get(position).getName());
        viewHolder.checkbox.setChecked(list.get(position).isCheck());

        return convertView;
    }
}
