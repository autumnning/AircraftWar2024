package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aircraftwar2024.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static ActivityManager activityManager;

    int music = 0;
    //test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button)findViewById(R.id.begin);
        btn1.setOnClickListener(this);

        activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(MainActivity.this);

        RadioGroup btn_music = (RadioGroup) findViewById(R.id.music);
        btn_music.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(group.getCheckedRadioButtonId() == R.id.radioButton3) {
                    music = 1;
                } else if (group.getCheckedRadioButtonId() == R.id.radioButton4) {
                    music = 0;
                }
            }
        });

    }

    public void onClick(View v){
        if (v.getId() == R.id.begin) {
            Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
            intent.putExtra("music", music);
            System.out.println("Main中music是"+music);
            startActivity(intent);
        }
    }

}