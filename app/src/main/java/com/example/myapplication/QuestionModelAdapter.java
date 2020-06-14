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

public class QuestionModelAdapter extends ArrayAdapter<QuestionModel> {

    private ArrayList<QuestionModel> questionList;
    Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView question;
        CheckBox checkbox;
    }

    //Making a listener to send data fro questionModelAdapter to Questionnaire Fragment
    public interface DataTransferListener {
        public void onDataTransfer(ArrayList<QuestionModel> data);
    }

    private DataTransferListener dataTransferListener;

    public void setDataTransferListener(DataTransferListener listener) {
        this.dataTransferListener = listener;
    }

    //Initialization
    public QuestionModelAdapter(@NonNull Context context, ArrayList<QuestionModel> questionList) {
        super(context, 0, questionList);
        this.context = context;
        this.questionList = questionList;
    }

    //Fill the listview
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // to add a custom view..

        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.question_model, parent, false);

            viewHolder.question = convertView.findViewById(R.id.question);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.check_box);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    questionList.get(getPosition).setCheck(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    dataTransferListener.onDataTransfer(questionList);
                }

            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.question, viewHolder.question);
            convertView.setTag(R.id.check_box, viewHolder.checkbox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkbox.setTag(position); // This line is important.

        viewHolder.question.setText(questionList.get(position).getQuestion());
        viewHolder.checkbox.setChecked(questionList.get(position).isCheck());

        return convertView;
    }
}
