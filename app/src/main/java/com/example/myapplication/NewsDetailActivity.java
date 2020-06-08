package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity {
    private ProgressBar spinner;
    WebView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        Intent intent = getIntent();
        String linkStr=intent.getStringExtra("link");
        browser = (WebView) findViewById(R.id.webview);
        browser.loadUrl(linkStr);
        browser.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                spinner.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
                spinner.setVisibility(View.GONE);
            }
        });
        }


}
