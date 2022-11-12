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

public class PTodoListsMain extends AppCompatActivity {
    //attributes
    private EditText mTitle;
    private Button mSaveBtn, mShowBtn;
    private FirebaseFirestore db;
    private String uTitle, uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_lists_main);

        mTitle = findViewById(R.id.edit_title);
        mSaveBtn = findViewById(R.id.save_btn);
        mShowBtn = findViewById(R.id.showall_btn);

        db= FirebaseFirestore.getInstance();//firebase fire store connection

        Bundle bundle = getIntent().getExtras();//pass data between activities
        if (bundle != null){
            mSaveBtn.setText("Update");
            uTitle = bundle.getString("uTitle");
            uId = bundle.getString("uId");
            mTitle.setText(uTitle);
        }else{
            mSaveBtn.setText("Save");
        }

        mShowBtn.setOnClickListener(new View.OnClickListener() {//navigate to PShowActivity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PTodoListsMain.this , PShowActivity.class));
            }
        });


        mSaveBtn.setOnClickListener(new View.OnClickListener() {//display saved data to update
            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    updateToFireStore(id , title);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id , title);
                }

            }
        });
    }

    private void updateToFireStore(String id , String title){//update data

        db.collection("TodoLists").document(id).update("title" , title )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(PTodoListsMain.this, "Note Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PTodoListsMain.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PTodoListsMain.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveToFireStore(String id , String title){//insert data

        if (!title.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();//to map identifying values, known as keys
            map.put("id" , id);
            map.put("title" , title);


            db.collection("TodoLists").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PTodoListsMain.this, "Note Saved Successfully!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PTodoListsMain.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}


