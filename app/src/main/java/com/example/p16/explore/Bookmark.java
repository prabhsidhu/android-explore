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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Bookmark extends MyMenu {

    RecyclerView recyclerView;
    DatabaseReference mref;
    List<Multiple_view> list;
    MyAdapter adapter;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Bookmark");


        recyclerView = findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        list = new ArrayList<Multiple_view>();


        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        mref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Bookmark");


//        Log.d("Bookmark page", "onCreate: " + mref);
        mref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){

                    Multiple_view value = dataSnapshot2.getValue(Multiple_view.class);
                    Multiple_view v = new Multiple_view();
                    String key = dataSnapshot2.getKey();
                    String title = value.getName();
                    String image = value.getImage();
                    String description = value.getDescription();
                    String date = value.getDate();
                    String time = value.getTime();
                    String place = value.getPlace();
                    String address = value.getAddress();
                    String register =  value.getRegister();
                    String contact =  value.getContact();
                    v.setBookmark(true);
                    v.setKey(key);
                    v.setImage(image);
                    v.setName(title);
                    v.setDescription(description);
                    v.setDate(date);
                    v.setTime(time);
                    v.setAddress(address);
                    v.setPlace(place);
                    v.setRegister(register);
                    v.setContact(contact);
                    list.add(v);
                }
                adapter = new MyAdapter(Bookmark.this,list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Bookmark.this, "Something wrong", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "You are on bookmark page",Toast.LENGTH_LONG).show();
    }
}
