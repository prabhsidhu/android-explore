package com.example.p16.explore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class List_page extends MyMenu {


    RecyclerView recyclerView;
    DatabaseReference mref;
    List<Multiple_view> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

        Bundle bundle =  getIntent().getExtras();
        String title = bundle.getString("actionBarTitle");
        String key = bundle.getString("Key");
        String child = bundle.getString("Child");

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(title);


        recyclerView = findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<Multiple_view>();


        Log.d("Title", "onCreate: " + title);
        Log.d("Child", "onCreate: " + child);

        mref = FirebaseDatabase.getInstance().getReference(child).child(key);
                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            list.clear();
                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

//                              Log.d("Database", "keyValue: " + key);
                                Multiple_view value = dataSnapshot1.getValue(Multiple_view.class);
                                Multiple_view v = new Multiple_view();
                                String key = dataSnapshot1.getKey();
                                String title = value.getName();
                                String description = value.getDescription();
                                String image = value.getImage();
                               String date = value.getDate();
//                                String time = value.getTime();

                                String place = value.getPlace();
                                String address = value.getAddress();
                                String register =  value.getRegister();
                                String contact =  value.getContact();
                                String menu = value.getMenu();

                                Log.d("test contact", "onDataChange: " + value.getContact());
                                v.setBookmark(false);
                                v.setKey(key);
                                v.setName(title);
                                v.setImage(image);
                                v.setDescription(description);
                                v.setDate(date);

                                v.setBookmark(false);
//                                v.setTime(time);
                                v.setAddress(address);
                                v.setPlace(place);
                                v.setRegister(register);
                                v.setContact(contact);
                                v.setMenu(menu);

                                list.add(v);
                }
               adapter = new MyAdapter(List_page.this,list);
               recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(List_page.this, "something wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void homeMethod(android.view.View v) {

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
