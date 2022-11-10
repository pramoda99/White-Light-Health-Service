package com.example.white_light;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rtugeek.android.colorseekbar.ColorSeekBar;

import java.text.DateFormat;
import java.util.Calendar;

public class AllPdecoRoomMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgmpdeco_room_main);

        final TextView textView = findViewById(R.id.text_view);
        ColorSeekBar colorSeekBar = findViewById(R.id.color_seek_bar);

        colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i, int i1, int i2) {
                textView.setTextColor(i2);
            }
        });
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);
    }
}