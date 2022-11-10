package com.example.white_light;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {
    private ImageButton flashCardsBtn;
    private ImageButton toDoListBtn;
    private ImageButton chillHitsBtn;
    private ImageButton pomodoroBtn;
    private ImageButton decoRoomBtn;
    private ImageButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        flashCardsBtn = findViewById(R.id.flashcards_btn);
        toDoListBtn = findViewById(R.id.todolist_btn);
        chillHitsBtn = findViewById(R.id.chillhits_btn);
        pomodoroBtn= findViewById(R.id.pomodoro_btn);
        decoRoomBtn= findViewById(R.id.decoroom_btn);
        settingsBtn= findViewById(R.id.settings_btn);

        flashCardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this,SplashScreen.class));

            }
        });
        toDoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this,PTodoListsMain.class));

            }
        });
        chillHitsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this,GFlashCardsMain.class));

            }
        });
        pomodoroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, GReadShowActivity.class));

            }
        });
        decoRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, AllPdecoRoomMain.class));

            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ZpomodoroMain.class));

            }
        });
    }
}