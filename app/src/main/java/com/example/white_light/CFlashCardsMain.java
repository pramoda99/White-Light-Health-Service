package com.example.white_light;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class CFlashCardsMain extends AppCompatActivity {
    //attributes
    private EditText mAge, meHeight, mWeight;
    private Button mSaveBtn, mShowBtn;


    private FirebaseFirestore db;
    private String uAge,uHeight,uWeight,uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cflash_cards_main);

        mAge = findViewById(R.id.edit_age);
        meHeight = findViewById(R.id.edit_height);
        mWeight = findViewById(R.id.edit_weight);
        mSaveBtn = findViewById(R.id.save_btn);
        mShowBtn= findViewById(R.id.showall_btn);



        db = FirebaseFirestore.getInstance();//firebase fire store connection

        Bundle bundle = getIntent().getExtras();//pass data between activities
        if(bundle != null){
            mSaveBtn.setText("Update");
            uAge = bundle.getString("uAge");
            uId = bundle.getString("uId");
            uHeight = bundle.getString("uHeight");
            uWeight = bundle.getString("uWeight");
            mAge.setText(uAge);
            meHeight.setText(uHeight);
            mWeight.setText(uWeight);
        }else{
            mSaveBtn.setText("Save");
        }

        mShowBtn.setOnClickListener(new View.OnClickListener() {//navigate to ZShowActivity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CFlashCardsMain.this, CShowActivity.class));
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {//display saved data to update
            @Override
            public void onClick(View v) {
                String age = mAge.getText().toString();
                String height = meHeight.getText().toString();
                String weight = mWeight.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 != null){
                    String id = uId;
                    updateToFireStore(id, age, height, weight);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, age, height, weight);
                }
            }
        });
    }

    private void updateToFireStore(String id, String age, String height, String weight) {//update data
        db.collection("BMI").document(id).update("age", age , "height",height, "weight", weight)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CFlashCardsMain.this, "Session Updated!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CFlashCardsMain.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CFlashCardsMain.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveToFireStore(String id, String age, String height, String weight) {//insert data
        if(!age.isEmpty() && !height.isEmpty()){
            HashMap<String,Object> map = new HashMap<>();//to map identifying values, known as keys
            map.put("id" ,id);
            map.put("age" ,age);
            map.put("height",height);
            map.put("weight",weight);

            db.collection("BMI").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(CFlashCardsMain.this, "Session Saved Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CFlashCardsMain.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else
            Toast.makeText(this, "Empty Fields Are Not Allowed", Toast.LENGTH_SHORT).show();
    }


}