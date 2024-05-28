package com.example.aircraftwar2024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class OfflineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        //改字体的
//        TextView font = findViewById(R.id.choice);
//        AssetManager mgr = getResources().getAssets();
//        Typeface tf = Typeface.createFromAsset(mgr,"fonts/STXINGKA.TTF");
//        font.setTypeface(tf);
    }

}