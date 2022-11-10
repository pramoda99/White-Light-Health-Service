package com.example.white_light;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private EditText mEmail, mPass;
    private Button signInBtn;
    private TextView mTextView;

    private FirebaseAuth mAuth;//firebase attribute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmail = findViewById(R.id.signin_email);
        mPass = findViewById(R.id.signin_password);
        signInBtn = findViewById(R.id.signin_btn);
        mTextView = findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();
        mTextView.setOnClickListener(new View.OnClickListener() {//sign in to sign up page
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
        signInBtn.setOnClickListener((v)->{loginUser(); });
    }
    private void loginUser() {
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();

        if(!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!pass.isEmpty()) {
               mAuth.signInWithEmailAndPassword(email,pass)//match with email & password
                       .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                           @Override
                           public void onSuccess(AuthResult authResult) {
                               Toast.makeText(SignIn.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignIn.this,HomeActivity.class));
                                finish();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(SignIn.this, "Login Failed!", Toast.LENGTH_SHORT).show();
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