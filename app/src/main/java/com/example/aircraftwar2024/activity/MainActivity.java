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
            new Thread(new NetConn(handler)).start();
            Intent intent = new Intent(MainActivity.this, OnlineActivity.class);
            intent.putExtra("music", music);
            startActivity(intent);
        }
    }

    protected class NetConn extends Thread{
        private BufferedReader in;
        private Handler toClientHandler;

        public NetConn(Handler myHandler){
            this.toClientHandler = myHandler;
        }
        @Override
        public void run(){
            try{
                socket = new Socket();

                socket.connect(new InetSocketAddress
                        ("10.0.2.2",9999),5000);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),"utf-8")),true);
                Log.i(TAG,"connect to server");

                //接收服务器返回的数据
                Thread receiveServerMsg =  new Thread(){
                    @Override
                    public void run(){
                        String fromserver = null;
                        try{
                            while((fromserver = in.readLine())!=null)
                            {
                                //发送消息给UI线程    发送分数
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = fromserver;
                                toClientHandler.sendMessage(msg);
                            }
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }
                };
                receiveServerMsg.start();
            }catch(UnknownHostException ex){
                ex.printStackTrace();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

}