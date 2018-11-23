package com.example.p16.explore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private EditText Uname , Upass ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        Uname = (EditText) findViewById(R.id.usernameSign);
        Upass = (EditText) findViewById(R.id.passwordSign);

        Button home = (Button) findViewById(R.id.btnSign);
        mAuth = FirebaseAuth.getInstance();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent home = new Intent(SignIn.this, home.class) ;
//                startActivity(home);
                userLogin();
            }
        });

    }
    private void userLogin(){
        final String userName  = Uname.getText().toString().trim();
        final String password  = Upass.getText().toString().trim();

        if (userName.isEmpty()) {
            Uname.setError(getString(R.string.input_error_name));
            Uname.requestFocus();
            return;
        }

        if (userName.isEmpty()) {
            Uname.setError(getString(R.string.input_error_email));
            Uname.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Upass.setError(getString(R.string.input_error_password));
            Upass.requestFocus();
            return;
        }

//        if (password.length() < 6) {
//            Upass.setError(getString(R.string.input_error_password_length));
//            Upass.requestFocus();
//            return;
//        }


        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getInstance().getCurrentUser();
                            updateUI(user);
                            if(user !=null) {
                                Intent i = new Intent(getApplicationContext(), home.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(SignIn.this, "User Needs to Login" + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Tag", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }

                    private void updateUI(FirebaseUser user) {
                        if(user != null){
                            Log.d("TAG", "updateUI-email: " + user.getEmail());
                            Log.d("Tag", "updateUI-id: " + user.getUid());
                        }else{
                            Log.d("Firebase User", "updateUI: user is not logged in");

                        }
                    }
                });
    }
}
