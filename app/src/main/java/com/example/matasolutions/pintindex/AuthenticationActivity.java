package com.example.matasolutions.pintindex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private EditText password;
    private EditText email;
    private Button button_register;
    private Button button_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

            email = (EditText) findViewById(R.id.signup_email_input);
            password =(EditText) findViewById(R.id.signup_password_input);
            button_register = (Button)findViewById(R.id.button_register);
            button_login = (Button)findViewById(R.id.button_login);
            FirebaseApp.initializeApp(this);
            mAuth = FirebaseAuth.getInstance();

            button_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(view == button_register){

                        RegisterUser();

                    }
                }
            });

            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(view == button_login){

                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                    }

                }
            });

        }


    public void RegisterUser(){
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        if (TextUtils.isEmpty(Email)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            //check if successful
                            if (task.isSuccessful()) {
                                //User is successfully registered and logged in
                                //start Profile Activity here
                                Toast.makeText(AuthenticationActivity.this, "Registration successful",
                                        Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            }else{
                                Toast.makeText(AuthenticationActivity.this, "Couldn't register, try again",
                                        Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }







}