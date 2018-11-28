package com.example.p16.explore;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

//import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Single_view extends MyMenu {

    TextView name, date, description, key, address, txtMenu;
    ImageView image;
    private FirebaseAuth mAuth;
    ImageButton bookmark;
    FirebaseDatabase databaseRef = FirebaseDatabase.getInstance();
    String imgUri , place, addr, eventDate, eventTime, phonenumber, registerTxt;
    //getting current user
    FirebaseUser user = mAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);

        Bundle b = getIntent().getExtras();
        final Multiple_view d = (Multiple_view) b.getSerializable("data");

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(d.getName());

        image = (ImageView) findViewById(R.id.eventPic);
        name = (TextView) findViewById(R.id.name_title);
        description = (TextView) findViewById(R.id.event_description);
        date = (TextView) findViewById(R.id.txtDate);
        address = (TextView) findViewById(R.id.address);
        key = new TextView(this);
        txtMenu = (TextView) findViewById(R.id.ChkMenu);

        place = d.getPlace();
        addr = d.getAddress();
        eventDate = d.getDate();
        eventTime = d.getTime();
        imgUri = d.getImage();
        phonenumber = d.getContact();
        registerTxt = d.getRegister();
        String eAddress = place + " , " + addr;
        String eTimeDate = eventDate + " at " + eventTime;

        // check if the page open the resturant information
        if(d.getMenu() == null){
            txtMenu.setVisibility(View.GONE);
        }

        // hide the date field if its on food, tranquility and venture page
        if (d.getDate() == null) {
            date.setVisibility(View.GONE);
        }


        // set values to each fields
        key.setText(d.getKey());
        name.setText(d.getName());
        date.setText(eTimeDate);
        Picasso.get().load(d.getImage()).into(image);
        description.setText(d.getDescription());
        address.setText(eAddress);


        // Contact button for call on the phone number
        ImageButton contact = (ImageButton) findViewById(R.id.contact);
        if(d.getContact() == null){
            contact.setVisibility(View.GONE);
        }
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + d.getContact()));
                startActivity(intent);
            }
        });

         // location button (open the location page with google maps)
        ImageButton location = (ImageButton) findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Single_view.this, Location_page.class);
                i.putExtra("address", d.getAddress());
                Log.d("Single view page", "onClick: Address" + d.getAddress());
                i.putExtra("place", d.getPlace());
                startActivity(i);
            }
        });


        // check if user already stored this in bookmark
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Bookmark");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(key.getText().toString())) {
                    Log.d("Bookmark", "onDataChange: " + dataSnapshot.hasChild(key.getText().toString()));
                    bookmark.setVisibility(View.INVISIBLE);
                } else {
                    bookmark.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // bookmark button click to save this info in user bookmark table
        bookmark = (ImageButton) findViewById(R.id.bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookmarkMethod();
            }
        });

        Button register = (Button) findViewById(R.id.btnRegistration);
// check if event has registration option
        if (registerTxt == null) {
            register.setVisibility(View.GONE);
        } else {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent registerIntent = new Intent(Single_view.this, RegistrationView.class);
                    registerIntent.putExtra("Title", name.getText().toString());
                    registerIntent.putExtra("Url", registerTxt);
                    startActivity(registerIntent);
                }
            });

        }
    }


    // bookmark methos for bookmark button
    private void bookmarkMethod() {
        // saving the information into bookmark table in current user table
         DatabaseReference mRef = databaseRef.getReference("Users/" + user.getUid()).child("Bookmark").child(key.getText().toString()) ;
         mRef.child("Name").setValue(name.getText().toString());
         mRef.child("Image").setValue(imgUri);
         mRef.child("Address").setValue(addr);
         mRef.child("Date").setValue(eventDate);
         mRef.child("Time").setValue(eventTime);
         mRef.child("Place").setValue(place);
         mRef.child("Contact").setValue(phonenumber);
         if(registerTxt != null){
             mRef.child("Register").setValue(registerTxt);
         }
         mRef.child("Description").setValue(description.getText().toString()).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Log.d("BookMark", "onComplete: Stored" );
                           Toast.makeText(getApplicationContext(), "Saved as Bookmark", Toast.LENGTH_SHORT).show();
                           bookmark.setVisibility(View.INVISIBLE);
                       }
                       else{
                           Log.d("BookMark", "onComplete: Not Stored");
                       }
                }
            });
      //

    }
// footer icons methods to call different pages
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
