package com.example.myapplication.Questions;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Questions.QuestionModel;
import com.example.myapplication.Questions.QuestionModelAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;

//Questionnaire implements DataTransferListener interface from QuestionModelAdapter
public class Questionnaire extends Fragment implements QuestionModelAdapter.DataTransferListener {
    private ListView questionListView;
    private QuestionModelAdapter questionModelAdapter;
    private Button saveButton;

    public Questionnaire() {
        // Required empty public constructor
    }

    //Receive question and isChecked from questionModelAdapter
    @Override
    public void onDataTransfer(ArrayList<QuestionModel> data) {
        for(QuestionModel res: data) {
            Log.i("INFO",res.getQuestion() + " " + res.isCheck());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<QuestionModel> questionList = new ArrayList<>();
        questionList.add(new QuestionModel(getResources().getString(R.string.qs1),false));
        questionList.add(new QuestionModel(getResources().getString(R.string.qs2),false));
        questionList.add(new QuestionModel(getResources().getString(R.string.qs3),false));
        questionList.add(new QuestionModel(getResources().getString(R.string.qs4),false));
        questionList.add(new QuestionModel(getResources().getString(R.string.qs5),false));
        questionList.add(new QuestionModel(getResources().getString(R.string.qs6),false));
        questionList.add(new QuestionModel(getResources().getString(R.string.qs7),false));

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.question_fragment, container, false);
        questionListView= rootView.findViewById(R.id.questionListView);
        questionModelAdapter = new QuestionModelAdapter(this.getContext(), questionList);
        questionListView.setAdapter(questionModelAdapter);
        questionModelAdapter.setDataTransferListener(this); //setting up the listener

        //button to save the questions and answers in local database
        saveButton = rootView.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO", "save the data");
            }
        });
        return rootView;
    }
}
