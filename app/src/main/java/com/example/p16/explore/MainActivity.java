package com.example.p16.explore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


            private FirebaseAuth auth;
            GoogleSignInClient mGoogleSignInClient;
            SignInButton signInButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button sign_In = (Button) findViewById(R.id.signIn);
        Button sign_up = (Button) findViewById(R.id.signUp);

        sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignIn.class);
                startActivity(i);
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignUP.class);
                startActivity(i);
            }
        });


// Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        auth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 9001);
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = auth.getInstance().getCurrentUser();
        updateUI(user);
    }

    @Override
    public void onBackPressed() {
//  empty so nothing happens
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                Log.d("Tag", "onActivityResult: sign in successfully");
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("tag","sign in failed" );
//                updateUI(null);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = auth.getInstance().getCurrentUser();
                            updateUI(user);
//                            String name = user.getDisplayName();
                            myRef.child(user.getUid()).child("Name").setValue(user.getDisplayName()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.d("Firebase User", "stored");

                                    }else{
                                        Log.d("Firebase User", "Not stored");
                                    }
                                }
                            });

                            if(user !=null) {
                                String uName = user.getDisplayName();
                                Intent i = new Intent(getApplicationContext(), home.class);
                                startActivity(i);
                                Toast.makeText(MainActivity.this,uName + " Logged in",Toast.LENGTH_SHORT ).show();
                                updateUI(user);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "User Needs to Login" + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }

                            Log.d("tag", "onComplete: going to next activity");

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"Could not log in" + task.getException(),Toast.LENGTH_SHORT ).show();
                            updateUI(null);
                        }
                    }
                });

    }


    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            Log.d("TAG", "updateUI-email-id: " + currentUser.getEmail());
            Log.d("Tag", "updateUI-id: " + currentUser.getUid());
            Intent i = new Intent(getApplicationContext(),home.class);
            startActivity(i);
        }
           else{
            Log.d("TAG","user logged out");
        }
    }
}
