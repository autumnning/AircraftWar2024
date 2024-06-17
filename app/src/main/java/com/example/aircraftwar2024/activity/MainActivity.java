package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aircraftwar2024.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static ActivityManager activityManager;

    private Socket socket;
    private PrintWriter writer;
    private Handler handler;
    private EditText txt;
    private static  final String TAG = "MainActivity";

    int music = 0;
    //test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button)findViewById(R.id.begin);
        btn1.setOnClickListener(this);
        Button btn2 = (Button)findViewById(R.id.lianji);
        btn2.setOnClickListener(this);

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
            startActivity(intent);
        } else if (v.getId() == R.id.lianji) {
//            new Thread(new NetConn(handler)).start();
            Intent intent = new Intent(MainActivity.this, OnlineActivity.class);
            intent.putExtra("music", music);
            startActivity(intent);
        }
    }


}