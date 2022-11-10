package com.example.white_light;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class GFlashCardsMain extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    //attributes
    private EditText mTitle , mDesc, mAuthor, mDate;
    private Button mSaveBtn, mShowBtn;
    private FirebaseFirestore db;
    private String uTitle, uDesc , uId, uAuthor, uDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article_main);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        mTitle = findViewById(R.id.edit_title);
        mDesc = findViewById(R.id.edit_desc);
        mAuthor = findViewById(R.id.edit_title2);
        mDate = findViewById(R.id.editTextDate);
        mSaveBtn = findViewById(R.id.save_btn);
        mShowBtn = findViewById(R.id.showall_btn);

        db= FirebaseFirestore.getInstance();//firebase fire store connection

        Bundle bundle = getIntent().getExtras();//pass data between activities
        if (bundle != null){
            mSaveBtn.setText("Update");
            uTitle = bundle.getString("uTitle");
            uId = bundle.getString("uId");
            uDesc = bundle.getString("uDesc");
            uAuthor = bundle.getString("uAuthor");
            uDate = bundle.getString("uDate");
            mTitle.setText(uTitle);
            mDesc.setText(uDesc);
            mAuthor.setText( uAuthor);
            mDate.setText( uDate);
        }else{
            mSaveBtn.setText("Save");
        }

        mShowBtn.setOnClickListener(new View.OnClickListener() {//navigate to GShowActivity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GFlashCardsMain.this , GShowActivity.class));
            }
        });


        mSaveBtn.setOnClickListener(new View.OnClickListener() {//display saved data to update
            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString();
                String desc = mDesc.getText().toString();
                String author = mAuthor.getText().toString();
                String date = mDate.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    updateToFireStore(id , title, desc, author, date);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id , title , desc, author, date);
                }
                startActivity(new Intent(GFlashCardsMain.this , GShowActivity.class));
            }

        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        EditText editText = (EditText) findViewById(R.id.editTextDate);
        editText.setText(currentDateString);
    }

    private void updateToFireStore(String id , String title , String desc, String author, String date){//update data

        db.collection("Articles").document(id).update("title" , title , "desc" , desc, "author", author, "date", date)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(GFlashCardsMain.this, "Article Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(GFlashCardsMain.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GFlashCardsMain.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void saveToFireStore(String id , String title , String desc, String author, String date){//insert data

        if (!title.isEmpty() && !desc.isEmpty() && !author.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();//to map identifying values, known as keys
            map.put("id" , id);
            map.put("title" , title);
            map.put("desc" , desc);
            map.put("author" , author);
            map.put("date" , date);

            db.collection("Articles").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(GFlashCardsMain.this, "Article Published Successfully !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GFlashCardsMain.this, "Failed !!", Toast.LENGTH_SHORT).show();
                        }
                    });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();

    }

}