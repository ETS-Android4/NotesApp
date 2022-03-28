package com.example.locationnotesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SignupScreen extends AppCompatActivity{

        EditText mFullName,mEmail,mPassword,mPhone;
        Button mRegisterButton;
        TextView mLogin;
        FirebaseAuth auth;


        @Override
        protected void onCreate(Bundle saveInstanceState){
            super.onCreate(saveInstanceState);
            setContentView(R.layout.activity_signupscreen);
            findByView();

            auth= FirebaseAuth.getInstance();

            if(auth.getCurrentUser()!=null){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

            mRegisterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email= mEmail.getText().toString().trim();
                    String password= mPassword.getText().toString().trim();

                    if(TextUtils.isEmpty(email)){
                        mEmail.setError("email is required");
                        return;
                    }
                    if(TextUtils.isEmpty(password)){
                        mPassword.setError("password is required");
                        return;
                    }
                    if(password.length()<6){
                        mPassword.setError("password must be >=6 characters");
                    }



                    //register user to firebase

                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupScreen.this,"User created",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            }else {
                                Toast.makeText(SignupScreen.this,"Error!" +task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });



                }
            });
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), LoginScreen.class));
                }
            });


        }


    private void findByView(){

        mEmail= findViewById(R.id.email);
        mFullName= findViewById(R.id.name);
        mPassword= findViewById(R.id.password);
        mPhone= findViewById(R.id.phone_number);
        mRegisterButton= findViewById(R.id.registerBtn);
        mLogin= findViewById(R.id.loginText);

    }
}
