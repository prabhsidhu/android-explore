package com.example.p16.explore;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tour_page extends MyMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_page);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Tour");


         Button tranquility, venture;
         tranquility = (Button) findViewById(R.id.btnTranquility);
         venture = (Button) findViewById(R.id.btnVenture);

         tranquility.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(Tour_page.this,List_page.class);
                 i.putExtra("Child", "Tranquility");
                 i.putExtra("actionBarTitle", "Tranquility");
                 i.putExtra("Key", "places");

                 startActivity(i);
             }
         });
        venture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tour_page.this,List_page.class);
                i.putExtra("Child", "Venture");
                i.putExtra("actionBarTitle", "Venture");
                i.putExtra("Key", "places");
                startActivity(i);
            }
        });



    }





    public void homeMethod(View v) {
        startActivity(new Intent(this,home.class));
    }
    public void BookmarkMethod(View v) {
        Intent i = new Intent(this, Bookmark.class);
        startActivity(i);
    }
    public void SearchMethod(View v) {
        Intent i = new Intent(this, Search_Page.class);
        startActivity(i);
    }
}
