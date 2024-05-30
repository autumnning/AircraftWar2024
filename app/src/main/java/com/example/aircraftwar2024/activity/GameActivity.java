package com.example.aircraftwar2024.activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;


public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    public static Handler mHandler;

    private int gameType=0;
    public static int screenWidth,screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getScreenHW();
        mHandler = new Mhandler();

        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
        }

        /*TODO:根据用户选择的难度加载相应的游戏界面*/
        BaseGame baseGameView = null;
        if (gameType == 1) {
            baseGameView = new EasyGame(this);
        } else if (gameType == 2) {
            baseGameView = new MediumGame(this);

        } else if (gameType == 3) {
            baseGameView = new HardGame(this);
        }
        setContentView(baseGameView);

    }

    public void getScreenHW(){
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        //窗口的宽度
        screenWidth= dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
        System.out.println("githubpush");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    class Mhandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    System.out.println("bbbb");
                    setContentView(R.layout.activity_record);
                    break;
            }
        }
    }

}