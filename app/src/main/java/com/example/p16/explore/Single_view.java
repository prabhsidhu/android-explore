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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Single_view extends MyMenu {

    TextView name, date, description, key;
    ImageView image;
    private FirebaseAuth mAuth;
 ImageButton bookmark;
    FirebaseDatabase databaseRef = FirebaseDatabase.getInstance();
    DatabaseReference mref = databaseRef.getReference("Users") ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);

        Bundle b =  getIntent().getExtras();
        final Multiple_view d = (Multiple_view) b.getSerializable("data");
        Log.d("testing", d.getName());

        final String sName = b.getString("Name");



        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(d.getName());


        image = (ImageView) findViewById(R.id.eventPic);
        name = (TextView) findViewById(R.id.name_title);
        description = (TextView) findViewById(R.id.event_description);

        key = new TextView(this);
         key.setText(d.getKey());
        name.setText(d.getName());

        ImageButton contact = (ImageButton) findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        ImageButton location = (ImageButton) findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Single_view.this,Location_page.class);
                startActivity(i);
            }
        });
        bookmark = (ImageButton) findViewById(R.id.bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
//                setVisible(false);
//                bookmark.setVisibility(View.INVISIBLE);
               Log.d("Test", "multiple view "+ d.getKey());

               bookmarkMethod();
            }
        });
        Button register = (Button) findViewById(R.id.btnRegistration);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Intent registerIntent = new Intent( Single_view.this,RegistrationView.class);
             registerIntent.putExtra("Title", sName);
                startActivity(registerIntent);
            }
        });

    }

    private void bookmarkMethod() {
        Log.d("BookMark", "bookmarkMethod: " + key);
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        Log.d("Firebase user", "bookmarkMethod: "+ user.getUid());
        DatabaseReference bookmarkRef = mref.child(user.getUid()).child("Bookmark").child(key.getText().toString());
       bookmarkRef.child("Name").setValue(name.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){
                       Log.d("BookMark", "onComplete: Stored" );
                       bookmark.setVisibility(View.INVISIBLE);
                   }
                   else{
                       Log.d("BookMark", "onComplete: Not Stored");
                   }
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
