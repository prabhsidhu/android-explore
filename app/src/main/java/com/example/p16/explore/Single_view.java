package com.example.p16.explore;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Base64Utils;
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

import org.w3c.dom.Text;

public class Single_view extends MyMenu {

    TextView name, date, description, key, address, txtMenu;
    ImageView image;
    private FirebaseAuth mAuth;
    ImageButton bookmark;
    FirebaseDatabase databaseRef = FirebaseDatabase.getInstance();

    //getting current user
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);

        Bundle b = getIntent().getExtras();
        final Multiple_view d = (Multiple_view) b.getSerializable("data");
        final String sName = b.getString("Name");


        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(d.getName());


        image = (ImageView) findViewById(R.id.eventPic);
        name = (TextView) findViewById(R.id.name_title);
        description = (TextView) findViewById(R.id.event_description);
        date = (TextView) findViewById(R.id.txtDate);
        address = (TextView) findViewById(R.id.address);
        key = new TextView(this);
        txtMenu = (TextView) findViewById(R.id.ChkMenu);

        if(d.getMenu() == null){
            txtMenu.setVisibility(View.GONE);
        }

        if (d.getDate() == null) {
            date.setVisibility(View.GONE);
        }


        // set values to each fields
        key.setText(d.getKey());
        name.setText(d.getName());
        Picasso.get().load(d.getImage()).into(image);
//        date.setText(d.getDate() + " at " );
        description.setText(d.getDescription());
        address.setText(d.getPlace() + " , " + d.getAddress());


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

        ImageButton location = (ImageButton) findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Single_view.this, Location_page.class);
                i.putExtra("address", d.getAddress());
                i.putExtra("place", d.getPlace());
                startActivity(i);
            }
        });


        // check if user already stored event in bookmark

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Bookmark");
        mref.addValueEventListener(new ValueEventListener() {
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

        bookmark = (ImageButton) findViewById(R.id.bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookmarkMethod();
            }
        });
        Button register = (Button) findViewById(R.id.btnRegistration);

        if (d.getRegister() == null) {
            register.setVisibility(View.GONE);
        } else {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent registerIntent = new Intent(Single_view.this, RegistrationView.class);
                    registerIntent.putExtra("Title", sName);
                    startActivity(registerIntent);
                }
            });

        }
    }
    private void bookmarkMethod() {
        Log.d("BookMark", "bookmarkMethod: " + key);

        Log.d("Firebase user", "bookmarkMethod: "+ user.getUid());
        // saving the information into bookmark table in current user table
        DatabaseReference mref = databaseRef.getReference("Users/" + user.getUid()).child("Bookmark").child(key.getText().toString()) ;

         mref.child("Name").setValue(name.getText().toString());
         mref.child("Description").setValue(description.getText().toString()).
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
