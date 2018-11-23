package com.example.p16.explore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUP extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText name, userName, password;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

         name = (EditText) findViewById(R.id.Name);
         userName = (EditText) findViewById(R.id.UserName);
         password = (EditText) findViewById(R.id.password);
        Button   signUp = (Button) findViewById(R.id.signUPBtn);

        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         registerUser();
            }
        });

    }


    private void registerUser() {
        final String username =  name.getText().toString().trim();
        final String email = userName.getText().toString().trim();
       final String UserPassword = password.getText().toString().trim();


        if (username.isEmpty()) {
           name.setError(getString(R.string.input_error_name));
            name.requestFocus();
            return;
        }

        if (email.isEmpty()) {
           userName.setError(getString(R.string.input_error_email));
            userName.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
           userName.setError(getString(R.string.input_error_email_invalid));
            userName.requestFocus();
            return;
        }

        if (UserPassword.isEmpty()) {
            password.setError(getString(R.string.input_error_password));
            password.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,UserPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                             User signUpUser = new User(
                                     username,
                                     email,
                                     UserPassword
                             );

                            FirebaseUser user = mAuth.getInstance().getCurrentUser();
                            Log.d("userId" , user.getUid());
                            Log.d("Password" , UserPassword);
                            Log.d("Email",email);
                            Log.d("Name",username);
                            startActivity(new Intent(SignUP.this, SignIn.class));
                            Toast.makeText(SignUP.this, "user registered Succesfully", Toast.LENGTH_LONG).show();
                             myRef.child(user.getUid()).setValue(signUpUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful()){
                                         Log.d("SignUp", "stored");

                                     }else{
                                         Log.d("SignUp", "Not stored");
                                     }
                                 }
                             });



                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });

    }



 }
