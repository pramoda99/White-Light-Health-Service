package com.example.white_light;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    //attributes
    private EditText mEmail, mPass;
    private Button signUpBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = findViewById(R.id.email_reg);
        mPass = findViewById(R.id.password_reg);
        signUpBtn = findViewById(R.id.signup_btn);

        mAuth = FirebaseAuth.getInstance();//firebase authentication

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    // creating a new user
    private void createUser() {
        String email = mEmail.getText().toString();//string representation of an object
        String pass = mPass.getText().toString();

        if(!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()) {//email validation
            if (!pass.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this,SignIn.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Registration Error", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                mPass.setError("Empty Fields Are Not Allowed");
            }
        }else if(email.isEmpty()){
                mEmail.setError("Empty Fields Are Not Allowed");
            }
            else {
                mEmail.setError("Please Enter Valid Email");
            }
        }
    }
