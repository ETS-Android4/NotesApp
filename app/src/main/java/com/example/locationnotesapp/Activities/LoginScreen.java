package com.example.locationnotesapp.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locationnotesapp.MainActivity;
import com.example.locationnotesapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mLogin;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_loginscreen);
        findByView();

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser != null) {
            openApp();
        } else {


            mLoginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = mEmail.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        mEmail.setError("email is required");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        mPassword.setError("password is required");
                        return;
                    }
                    if (password.length() < 6) {
                        mPassword.setError("password must be >=6 characters");
                    }

                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginScreen.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            } else {
                                Toast.makeText(LoginScreen.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
            });
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), SignupScreen.class));
                }
            });
        }
    }


    private void openApp() {
        Log.d("pttt", "openApp");
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        finish();

    }


    private void findByView(){
        mEmail= findViewById(R.id.email);
        mPassword= findViewById(R.id.password);
        mLoginBtn= findViewById(R.id.loginBtn);
        mLogin= findViewById(R.id.loginText);

    }


}
