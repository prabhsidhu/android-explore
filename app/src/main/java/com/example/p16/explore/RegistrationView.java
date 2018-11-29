package com.example.p16.explore;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationView extends MyMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_view);

        Bundle b =  getIntent().getExtras();
        String title = b.getString("Title");
        String formUrl = b.getString("Url");

        TextView txtTitle = (TextView) findViewById(R.id.register);

        txtTitle.setText("Register for : " + title);


        WebView webView = (WebView) findViewById(R.id.register_view);
        webView.getSettings().setJavaScriptEnabled(true);


        // passing the form url of event regisration if event organisers have
        webView.loadUrl(formUrl);
        webView.setWebViewClient(new WebViewClient() {
            /*
                shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
                Give the host application a chance to take control when a URL is about to be loaded
                in the current WebView.

                In build.gradle,
                minSdkVersion 21
             */
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });
    }
    public void homeMethod(android.view.View v) {
        startActivity(new Intent(this, home.class));
    }


    public void BookmarkMethod(android.view.View v) {
        Intent i = new Intent(this, Bookmark.class);
        startActivity(i);
    }
    public void SearchMethod(View v) {
        Intent i = new Intent(this, Search_Page.class);
        startActivity(i);
    }
}
