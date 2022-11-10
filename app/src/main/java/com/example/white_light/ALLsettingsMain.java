package com.example.white_light;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ALLsettingsMain extends AppCompatActivity {
    private Button guide;
    private Button youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allsettingsmain);
        guide = findViewById(R.id.button);
        youtube = findViewById(R.id.youtube);

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ALLsettingsMain.this, listSettings.class));

            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://youtube.com/playlist?list=PL76x_et3muuQIPQeaOxCjiO5XidjcpBsc");
            }

        });
    }

    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}