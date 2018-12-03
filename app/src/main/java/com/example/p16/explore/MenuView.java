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

public class MenuView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);
        Bundle b =  getIntent().getExtras();
        String title = b.getString("MenuLink");
        String name = b.getString("Title");

        TextView txtTitle = (TextView) findViewById(R.id.chkmenu);
        txtTitle.setText(name);


        WebView webView = (WebView) findViewById(R.id.menuview);
        webView.getSettings().setJavaScriptEnabled(true);


        // passing the form url of event regisration if event organisers have
        webView.loadUrl(title);
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
