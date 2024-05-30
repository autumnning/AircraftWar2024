package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import com.example.aircraftwar2024.DAO.Player;
import com.example.aircraftwar2024.DAO.PlayerDAO;
import com.example.aircraftwar2024.DAO.PlayerDAOImpl;
import com.example.aircraftwar2024.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String music = "OFF";
    PlayerDAO playerDAO = new PlayerDAOImpl();
    //test
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button)findViewById(R.id.begin);
        btn1.setOnClickListener(this);

        RadioButton btn_musicON = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton btn_musicOFF = (RadioButton) findViewById(R.id.radioButton4);
        btn_musicON.setOnClickListener(this);
        btn_musicOFF.setOnClickListener(this);

//        setContentView(R.layout.activity_record);
//        ListView list = (ListView) findViewById(R.id.scoreTable);

        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(
                this,
                getData(),
                R.layout.activity_item,
                new String[]{"usrname","score","time"},
                new int[]{R.id.usrname,R.id.score,R.id.time});
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
    private List<Map<String,Object>> getData() {
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String,Object>();
        try{
            List<Player> players = playerDAO.getAllPlayer();
            for(Integer i = 1;i<= players.size();i++){
                map = new HashMap<String, Object>();
                map.put("rank", i.toString());
                map.put("name",players.get(i-1).getName());
                map.put("score",players.get(i-1).getScore());
                map.put("time",players.get(i-1).getDate());
                listitem.add(map);

            }
        }catch (Exception e){
            e.printStackTrace();
        }


//        map.put("score", "");
//        map.put("time", "");
//        listitem.add(map);
//

//
//        map = new HashMap<String, Object>();
//        map.put("usrname", "test");
//        map.put("score", "");
//        map.put("time", "");
//        listitem.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("usrname", "test");
//        map.put("score", "");
//        map.put("time", "");
//        listitem.add(map);


        return listitem;
    }
}