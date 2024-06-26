package com.example.aircraftwar2024.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.DAO.Player;
import com.example.aircraftwar2024.DAO.PlayerDAO;
import com.example.aircraftwar2024.DAO.PlayerDAOImpl;
import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;
import com.example.aircraftwar2024.music.MySoundPool;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    public static Handler mHandler;

    public static int getGameType() {
        return gameType;
    }

    private static int gameType=0;

    public static int screenWidth,screenHeight;

    private ListView list;

    public int music;
    public int gameMode;

    private BaseGame baseGameView = null;

    int score = 0;
    String enemyscore = "0";

    boolean MyOver = false;
    boolean EnemyOver = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getScreenHW();
        mHandler = new Mhandler();

        MainActivity.activityManager.addActivity(this);

        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
        }

        music = getIntent().getIntExtra("music", 0);
        gameMode = getIntent().getIntExtra("gameMode", 0);


        /*TODO:根据用户选择的难度加载相应的游戏界面*/
        if (gameType == 1) {
            baseGameView = new EasyGame(this, music, gameMode);
        } else if (gameType == 2) {
            baseGameView = new MediumGame(this, music, gameMode);
        } else if (gameType == 3) {
            baseGameView = new HardGame(this, music, gameMode);
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

    class Mhandler extends Handler implements View.OnClickListener{

        PrintWriter writer;

        @SuppressLint("SetTextI18n")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    setContentView(R.layout.activity_record);

                    Button btn_return = (Button) findViewById(R.id.button);
                    btn_return.setOnClickListener(Mhandler.this);

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
                    list = (ListView) findViewById(R.id.scoreTable);
                    //生成适配器的Item和动态数组对应的元素
                    SimpleAdapter listItemAdapter = new SimpleAdapter(
                            GameActivity.this,
                            getData(),
                            R.layout.activity_item,
                            new String[]{"rank","usrname","score","time"},
                            new int[]{R.id.rank,R.id.usrname,R.id.score,R.id.time});

                    //添加并且显示
                    list.setAdapter(listItemAdapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                            builder.setMessage("确认删除？");
                            builder.setTitle("提示");

                            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(GameActivity.this, "删除第" + (position + 1) + "条记录", Toast.LENGTH_SHORT).show();

                                    PlayerDAOImpl playerDAO = new PlayerDAOImpl(GameActivity.this);
                                    try {
//                                        System.out.println(playerDAO.getAllPlayer().size());
                                        playerDAO.doDelete(position);
                                        if(playerDAO.getAllPlayer().size() == 0){
//                                            list.removeAllViews();
//
//                                            TextView emptyTextView = new TextView(GameActivity.this);
//                                            emptyTextView.setText("No data");
//                                            list.addView(emptyTextView);
                                            setContentView(R.layout.activity_record);
                                            Button btn_return = (Button) findViewById(R.id.button);
                                            btn_return.setOnClickListener(Mhandler.this);

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
                                        }else{
                                            flushAdapter();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
//                                    System.out.println("1111");
//                                    List<Player> DataList = null;

//                                    try {
//
//                                    } catch (Exception e) {
//
//                                        e.printStackTrace();
//                                    }
//                                    try {
//                                        DataList = playerDAO.getAllPlayer();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }

//                                    try {
//                                        System.out.println(playerDAO.getAllPlayer().size());
//                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
//                                    }


                                }
                            });
                            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            builder.create().show();
                        }
                    });
                    break;
                case 2:
                    score = baseGameView.getScore();
                    MyOver = true;
                    new Thread(() -> {
                        try {
                            writer = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(
                                            ((MySocket) getApplication()).getSocket().getOutputStream(), "utf-8")), true);
                            writer.println("end");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                    if (EnemyOver&&MyOver) {
                        new Thread(() -> {
                            try {
                                writer = new PrintWriter(new BufferedWriter(
                                        new OutputStreamWriter(
                                                ((MySocket) getApplication()).getSocket().getOutputStream(), "utf-8")), true);
                                writer.println("end");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                        new Thread(() -> {
                            Log.i(TAG, "disconnect to server");
                            writer.println("bye");
                        }).start();
                        System.out.println("对面先结束");
                        setContentView(R.layout.activity_online_over);

                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btn_return_online = (Button) findViewById(R.id.return_online);
                        btn_return_online.setOnClickListener(Mhandler.this);

                        TextView MyScore = findViewById(R.id.textView3);
                        TextView EnemySocre = findViewById(R.id.textView4);
                        MyScore.setText("你的分数" + score);
                        EnemySocre.setText("对手分数" + enemyscore);
                        break;
                    }
                    break;
                case 3:
                    int score = baseGameView.getScore();
                    System.out.println(score);
                    int finalScore = score;
                    new Thread(()->{
                        try{
                            writer = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(
                                            ((MySocket)getApplication()).getSocket().getOutputStream(),"utf-8")),true);
                            writer.println(finalScore);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }).start();
                    break;
                case 4:
                    if(msg.obj != null){
                        enemyscore = msg.obj.toString();
                        baseGameView.setEnemyScore(msg.obj.toString());
                    }
                    break;
                case 5:
                    score = baseGameView.getScore();
                    System.out.println("自己先结束1");
                    EnemyOver = true;
                    if (MyOver&&EnemyOver) {
                        new Thread(() -> {
                            try {
                                writer = new PrintWriter(new BufferedWriter(
                                        new OutputStreamWriter(
                                                ((MySocket) getApplication()).getSocket().getOutputStream(), "utf-8")), true);
                                writer.println("end");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                        new Thread(() -> {
                            Log.i(TAG, "disconnect to server");
                            writer.println("bye");
                        }).start();
                        System.out.println("自己先结束2");
                        setContentView(R.layout.activity_online_over);

                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btn_return_online = (Button) findViewById(R.id.return_online);
                        btn_return_online.setOnClickListener(Mhandler.this);

                        TextView MyScore = findViewById(R.id.textView3);
                        TextView EnemySocre = findViewById(R.id.textView4);
                        MyScore.setText("你的分数"+score);
                        EnemySocre.setText("对手分数"+enemyscore);
                    }
                    System.out.println(score);
                    int finalScore1 = score;
                    new Thread(()->{
                        try{
                            writer = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(
                                            ((MySocket)getApplication()).getSocket().getOutputStream(),"utf-8")),true);
                            writer.println(finalScore1);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }).start();

            }

        }

        // 返回首页
        public void onClick(View v) {
            if(v.getId() == R.id.button) {
                MainActivity.activityManager.finishActivity(GameActivity.this);
            }else if(v.getId() == R.id.return_online) {
                MainActivity.activityManager.finishActivity(GameActivity.this);
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
        return listitem;
    }

    private void flushAdapter() {

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                getData(),
                R.layout.activity_item,
                new String[]{"rank", "usrname", "score", "time"},
                new int[]{R.id.rank, R.id.usrname, R.id.score, R.id.time}
        );

        // 添加并显示数据
        list.setAdapter(simpleAdapter);
    }

}