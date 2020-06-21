package com.example.myapplication.navBarActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {
    Button reportButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        reportButton = findViewById(R.id.reportButton);
    reportButton.setOnClickListener(   new View.OnClickListener() {
        @Override
        public void onClick(View v) {}});
    }

}