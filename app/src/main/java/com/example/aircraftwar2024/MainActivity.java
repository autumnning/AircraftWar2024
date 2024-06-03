package com.example.aircraftwar2024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static AppCompatActivity activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.begin);
        btn1.setOnClickListener(this);
    }

    public void onClick(View v){
        Intent intent = new Intent(MainActivity.this,OfflineActivity.class);
//        intent.putExtra("","");
        startActivity(intent);
    }
}