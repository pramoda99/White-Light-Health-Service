package com.example.study_with_teddy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GchillHitsMain extends AppCompatActivity {

    //attributes
    Button songs;
    Button favorite;
    Button youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gchill_hits_main);

         songs = findViewById(R.id.songs_btn);
         favorite = findViewById(R.id.fav_btn);
         youtube = findViewById(R.id.youtube_btn);


         songs.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(GchillHitsMain.this, GMusicPlayer.class));
             }
         });

         favorite.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                startActivity(new Intent(GchillHitsMain.this, GFavouritesMain.class));
             }
         });


        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com/");
            }

        });


    }
    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}



