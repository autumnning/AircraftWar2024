package com.example.aircraftwar2024.activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.DAO.Player;
import com.example.aircraftwar2024.DAO.PlayerDAOImpl;
import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    class Mhandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    setContentView(R.layout.activity_record);
                    if(gameType == 1){
                        TextView mode = findViewById(R.id.difficulty);
                        mode.setText("难度:简单");
                    }else if(gameType == 2){
                        TextView mode = findViewById(R.id.difficulty);
                        mode.setText("难度:普通");
                    }else{
                        TextView mode = findViewById(R.id.difficulty);
                        mode.setText("难度:困难");
                    }
                    ListView list = (ListView) findViewById(R.id.scoreTable);
                    //生成适配器的Item和动态数组对应的元素
                    SimpleAdapter listItemAdapter = new SimpleAdapter(
                            GameActivity.this,
                            getData(),
                            R.layout.activity_item,
                            new String[]{"rank","usrname","score","time"},
                            new int[]{R.id.rank,R.id.usrname,R.id.score,R.id.time});

                    //添加并且显示
                    list.setAdapter(listItemAdapter);
                    break;
            }
        }
    }

    private List<Map<String, Object>> getData() {
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        try{
            List<Player> players = new PlayerDAOImpl(GameActivity.this).getAllPlayer();
            for(Integer i = 1;i<=players.size();i++){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("usrname", players.get(i-1).getName());
                map.put("score", players.get(i-1).getScore());
                map.put("time", players.get(i-1).getDate());
                map.put("rank", i.toString());
                listitem.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(listitem.get(0));
        return listitem;
    }

}