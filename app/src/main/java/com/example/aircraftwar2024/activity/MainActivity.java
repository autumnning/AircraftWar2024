package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.aircraftwar2024.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static ActivityManager activityManager;

    String music = "OFF";
    //test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button)findViewById(R.id.begin);
        btn1.setOnClickListener(this);

        activityManager = new ActivityManager();
        activityManager.addActivity(MainActivity.this);

        RadioButton btn_musicON = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton btn_musicOFF = (RadioButton) findViewById(R.id.radioButton4);
        btn_musicON.setOnClickListener(this);
        btn_musicOFF.setOnClickListener(this);

    }

    public void onClick(View v){
        if (v.getId() == R.id.radioButton3) {
            music = "ON";
        } else if (v.getId() == R.id.radioButton4) {
            music = "OFF";
        } else if (v.getId() == R.id.begin) {
            Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
            intent.putExtra("music", music);

            startActivity(intent);
        }
    }

}