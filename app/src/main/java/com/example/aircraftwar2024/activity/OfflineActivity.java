package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aircraftwar2024.R;

public class OfflineActivity extends AppCompatActivity implements View.OnClickListener{

    int music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        //改字体的
//        TextView font = findViewById(R.id.choice);
//        AssetManager mgr = getResources().getAssets();
//        Typeface tf = Typeface.createFromAsset(mgr,"fonts/STXINGKA.TTF");
//        font.setTypeface(tf);

        MainActivity.activityManager.addActivity(this);

        music = getIntent().getIntExtra("music", 0);
        System.out.println("Main中music是"+music);

        Button btn_easy = (Button) findViewById(R.id.easyButton);
        Button btn_normal = (Button) findViewById(R.id.normalButton);
        Button btn_hard = (Button) findViewById(R.id.hardButton);
        btn_easy.setOnClickListener(this);
        btn_normal.setOnClickListener(this);
        btn_hard.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
        if(v.getId() == R.id.easyButton) {
            intent.putExtra("gameType", 1);
        } else if(v.getId() == R.id.normalButton) {
            intent.putExtra("gameType", 2);
        } else if(v.getId() == R.id.hardButton) {
            intent.putExtra("gameType", 3);
        }
        intent.putExtra("music", music);
        startActivity(intent);
        MainActivity.activityManager.finishActivity(OfflineActivity.this);
    }



}