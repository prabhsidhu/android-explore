package com.example.p16.explore;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

public class Culture_page extends MyMenu{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_page);

        Button eventIndia = (Button) findViewById(R.id.btnEventIndia);
        Button foodIndia = (Button) findViewById(R.id.btnFoodIndia);
        Button eventBrazil = (Button) findViewById(R.id.btnEventBrazil);
        Button foodBrazil = (Button) findViewById(R.id.btnFoodBrazil);

        ActionBar actionbar = getSupportActionBar();
//        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
//                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
//                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
//
//
//
//        TextView tv = new TextView(this);
//        tv.setText("Culture");
//        tv.setGravity(Gravity.CENTER_HORIZONTAL);
//        // Apply the layout parameters to TextView widget
//        tv.setLayoutParams(lp);
//
//        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionbar.setCustomView(tv);
        actionbar.setTitle("Culture");

        eventIndia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(Culture_page.this,List_page.class);
                i.putExtra("actionBarTitle", "India");
                i.putExtra("Key", "India");
                i.putExtra("Child", "Event");
                startActivity(i);

            }
        });

        eventBrazil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Culture_page.this,List_page.class);
                i.putExtra("actionBarTitle", "Brazil");
                i.putExtra("Key", "Brazil");
                i.putExtra("Child", "Event");
                startActivity(i);

            }
        });
        foodBrazil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Culture_page.this,List_page.class);
                i.putExtra("actionBarTitle", "Brazil");
                i.putExtra("Key", "Brazil");
                i.putExtra("Child", "Food");
                startActivity(i);

            }
        });
        foodIndia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Culture_page.this,List_page.class);
                i.putExtra("actionBarTitle", "India");
                i.putExtra("Key", "India");
                i.putExtra("Child", "Food");
                startActivity(i);

            }
        });
    }
    public void homeMethod(View v) {

        startActivity(new Intent(this,home.class));
    }

    public void SearchMethod(View v) {
        Intent i = new Intent(this, Search_Page.class);
        startActivity(i);
    }
    public void BookmarkMethod(View v) {
        Intent i = new Intent(this, Bookmark.class);
        startActivity(i);
    }
}
