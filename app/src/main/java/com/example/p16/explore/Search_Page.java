package com.example.p16.explore;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search_Page extends MyMenu {


    RecyclerView recyclerView;
    DatabaseReference mref;
    List<Multiple_view> list;
    MyAdapter adapter;

    EditText searchTxt ;
    Spinner  categories , country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__page);



        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Search");


        searchTxt = (EditText) findViewById(R.id.searchTxt);
        categories = (Spinner) findViewById(R.id.category);
        country = (Spinner) findViewById(R.id.country);

        ImageButton search = (ImageButton) findViewById(R.id.btnSearch);


        recyclerView = findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<Multiple_view>();


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = searchTxt.getText().toString();
                firebaseSearch(text);

            }
        });


    }

    private void firebaseSearch(String text) {

         TextView error = (TextView)findViewById(R.id.errorMsg);

        String countries, category;
        countries = country.getSelectedItem().toString();
        category = categories.getSelectedItem().toString();

        Log.d("Testing search", "firebaseSearch: "+ countries);
        Log.d("Testing search", "firebaseSearch: "+ category);

        mref= FirebaseDatabase.getInstance().getReference(category).child(countries);
        Query query = mref.orderByChild("Name").startAt(text).endAt(text+ "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Multiple_view value = dataSnapshot1.getValue(Multiple_view.class);
                    Multiple_view v = new Multiple_view();
                    String key = dataSnapshot1.getKey();
                    Log.d("Firebase search Query", "result" + key);

                    Log.d("Firebase search Query", "result" + value.getName());
                    String title = value.getName();
                    String description = value.getDescription();
                    String image = value.getImage();

                    v.setKey(key);
                    v.setName(title);
                    v.setImage(image);
                    v.setDescription(description);
                    list.add(v);
                }
                adapter = new MyAdapter(Search_Page.this,list);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Search_Page.this, "something wrong", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "You are on search page",Toast.LENGTH_LONG).show();
    }
}
