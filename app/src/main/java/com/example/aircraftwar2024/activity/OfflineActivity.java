package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircraftwar2024.DAO.Player;
import com.example.aircraftwar2024.DAO.PlayerDAOImpl;
import com.example.aircraftwar2024.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfflineActivity extends AppCompatActivity implements View.OnClickListener{

    int music;

    private static final String TAG = "GameActivity";

    public static Handler mHandler;

    private ListView list;

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
        intent.putExtra("gameMode", 0);
        startActivity(intent);
        MainActivity.activityManager.finishActivity(this);

    }


//    public class Ranking implements View.OnClickListener {
//
//        setContentView(R.layout.activity_record);
//
//        Button btn_return = (Button) findViewById(R.id.button);
//                    btn_return.setOnClickListener(this);
//
//                    if(GameActivity.getGameType()==1)
//
//        {
//            TextView mode = findViewById(R.id.difficulty);
//            mode.setText("难度:简单");
//        }else if(GameActivity.getGameType()==2)
//
//        {
//            TextView mode = findViewById(R.id.difficulty);
//            mode.setText("难度:普通");
//        }else
//
//        {
//            TextView mode = findViewById(R.id.difficulty);
//            mode.setText("难度:困难");
//        }
//
//        list =(ListView)
//
//        findViewById(R.id.scoreTable);
//
//        //生成适配器的Item和动态数组对应的元素
//        SimpleAdapter listItemAdapter = new SimpleAdapter(
//                OfflineActivity.this,
//                getData(),
//                R.layout.activity_item,
//                new String[]{"rank", "usrname", "score", "time"},
//                new int[]{R.id.rank, R.id.usrname, R.id.score, R.id.time});
//
//        //添加并且显示
//                    list.setAdapter(listItemAdapter);
//
//                    list.setOnItemClickListener(new AdapterView.OnItemClickListener()
//
//        {
//            @Override
//            public void onItemClick (AdapterView < ? > adapterView, View view,int position, long id)
//            {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(OfflineActivity.this);
//                builder.setMessage("确认删除？");
//                builder.setTitle("提示");
//
//                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(OfflineActivity.this, "删除第" + (position + 1) + "条记录", Toast.LENGTH_SHORT).show();
//
//                        PlayerDAOImpl playerDAO = new PlayerDAOImpl(OfflineActivity.this);
//                        try {
//                            System.out.println(playerDAO.getAllPlayer().size());
//                            playerDAO.doDelete(position);
//                            if (playerDAO.getAllPlayer().size() == 0) {
////                                            list.removeAllViews();
////
////                                            TextView emptyTextView = new TextView(GameActivity.this);
////                                            emptyTextView.setText("No data");
////                                            list.addView(emptyTextView);
//                                setContentView(R.layout.activity_record);
//                                Button btn_return = (Button) findViewById(R.id.button);
//                                btn_return.setOnClickListener(Mhandler.this);
//
//                                if (GameActivity.getGameType() == 1) {
//                                    TextView mode = findViewById(R.id.difficulty);
//                                    mode.setText("难度:简单");
//                                } else if (GameActivity.getGameType() == 2) {
//                                    TextView mode = findViewById(R.id.difficulty);
//                                    mode.setText("难度:普通");
//                                } else {
//                                    TextView mode = findViewById(R.id.difficulty);
//                                    mode.setText("难度:困难");
//                                }
//                            } else {
//                                flushAdapter();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
////                                    System.out.println("1111");
////                                    List<Player> DataList = null;
//
////                                    try {
////
////                                    } catch (Exception e) {
////
////                                        e.printStackTrace();
////                                    }
////                                    try {
////                                        DataList = playerDAO.getAllPlayer();
////                                    } catch (Exception e) {
////                                        e.printStackTrace();
////                                    }
//
////                                    try {
////                                        System.out.println(playerDAO.getAllPlayer().size());
////                                    } catch (Exception e) {
////                                        throw new RuntimeException(e);
////                                    }
//
//
//                    }
//                });
//                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                    }
//                });
//                builder.create().show();
//            }
//        });
//
//        public void onClick(View v) {
//            if(v.getId() == R.id.button) {
//                MainActivity.activityManager.finishActivity(OfflineActivity.this);
//            }
//        }
//    }
//
//    class Mhandler extends Handler implements View.OnClickListener{
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    setContentView(R.layout.activity_record);
//
//                    Button btn_return = (Button) findViewById(R.id.button);
//                    btn_return.setOnClickListener(this);
//
//                    if(GameActivity.getGameType() == 1){
//                        TextView mode = findViewById(R.id.difficulty);
//                        mode.setText("难度:简单");
//                    }else if(GameActivity.getGameType() == 2){
//                        TextView mode = findViewById(R.id.difficulty);
//                        mode.setText("难度:普通");
//                    }else{
//                        TextView mode = findViewById(R.id.difficulty);
//                        mode.setText("难度:困难");
//                    }
//                    list = (ListView) findViewById(R.id.scoreTable);
//                    //生成适配器的Item和动态数组对应的元素
//                    SimpleAdapter listItemAdapter = new SimpleAdapter(
//                            OfflineActivity.this,
//                            getData(),
//                            R.layout.activity_item,
//                            new String[]{"rank","usrname","score","time"},
//                            new int[]{R.id.rank,R.id.usrname,R.id.score,R.id.time});
//
//                    //添加并且显示
//                    list.setAdapter(listItemAdapter);
//
//                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineActivity.this);
//                            builder.setMessage("确认删除？");
//                            builder.setTitle("提示");
//
//                            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(OfflineActivity.this, "删除第" + (position + 1) + "条记录", Toast.LENGTH_SHORT).show();
//
//                                    PlayerDAOImpl playerDAO = new PlayerDAOImpl(OfflineActivity.this);
//                                    try {
//                                        System.out.println(playerDAO.getAllPlayer().size());
//                                        playerDAO.doDelete(position);
//                                        if(playerDAO.getAllPlayer().size() == 0){
////                                            list.removeAllViews();
////
////                                            TextView emptyTextView = new TextView(GameActivity.this);
////                                            emptyTextView.setText("No data");
////                                            list.addView(emptyTextView);
//                                            setContentView(R.layout.activity_record);
//                                            Button btn_return = (Button) findViewById(R.id.button);
//                                            btn_return.setOnClickListener(Mhandler.this);
//
//                                            if(GameActivity.getGameType() == 1){
//                                                TextView mode = findViewById(R.id.difficulty);
//                                                mode.setText("难度:简单");
//                                            }else if(GameActivity.getGameType() == 2){
//                                                TextView mode = findViewById(R.id.difficulty);
//                                                mode.setText("难度:普通");
//                                            }else{
//                                                TextView mode = findViewById(R.id.difficulty);
//                                                mode.setText("难度:困难");
//                                            }
//                                        }else{
//                                            flushAdapter();
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
////                                    System.out.println("1111");
////                                    List<Player> DataList = null;
//
////                                    try {
////
////                                    } catch (Exception e) {
////
////                                        e.printStackTrace();
////                                    }
////                                    try {
////                                        DataList = playerDAO.getAllPlayer();
////                                    } catch (Exception e) {
////                                        e.printStackTrace();
////                                    }
//
////                                    try {
////                                        System.out.println(playerDAO.getAllPlayer().size());
////                                    } catch (Exception e) {
////                                        throw new RuntimeException(e);
////                                    }
//
//
//                                }
//                            });
//                            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                }
//                            });
//                            builder.create().show();
//                        }
//                    });
//                    break;
//            }
//        }
//
//        // 返回首页
//        public void onClick(View v) {
//            if(v.getId() == R.id.button) {
//                MainActivity.activityManager.finishActivity(OfflineActivity.this);
//            }
//        }
//    }
//
//    private List<Map<String, Object>> getData() {
//        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
//        try{
//            List<Player> players = new PlayerDAOImpl(OfflineActivity.this).getAllPlayer();
//            for(Integer i = 1;i<=players.size();i++){
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("usrname", players.get(i-1).getName());
//                map.put("score", players.get(i-1).getScore());
//                map.put("time", players.get(i-1).getDate());
//                map.put("rank", i.toString());
//                listitem.add(map);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(listitem.get(0));
//        return listitem;
//    }
//
//    private void flushAdapter() {
//
//        SimpleAdapter simpleAdapter = new SimpleAdapter(
//                this,
//                getData(),
//                R.layout.activity_item,
//                new String[]{"rank", "usrname", "score", "time"},
//                new int[]{R.id.rank, R.id.usrname, R.id.score, R.id.time}
//        );
//
//        // 添加并显示数据
//        list.setAdapter(simpleAdapter);
//    }

}