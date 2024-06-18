package com.example.aircraftwar2024.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Objects;

public class OnlineActivity extends AppCompatActivity implements View.OnClickListener{

    int music;


    private Socket socket;

    public int getMusic() {
        return music;
    }

    private PrintWriter writer;
    private Handler handler;
    private EditText txt;
    String fromserver = null;
    private static  final String TAG = "OnlineActivity";

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("hhhh");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connover);
        //改字体的
//        TextView font = findViewById(R.id.choice);
//        AssetManager mgr = getResources().getAssets();
//        Typeface tf = Typeface.createFromAsset(mgr,"fonts/STXINGKA.TTF");
//        font.setTypeface(tf);

        MainActivity.activityManager.addActivity(this);

        music = getIntent().getIntExtra("music", 0);

        Thread dd = new Thread(new NetConn(GameActivity.mHandler));
        dd.start();

        System.out.println(dd.toString());

        Button btn_easy = (Button) findViewById(R.id.easyButton);
        Button btn_normal = (Button) findViewById(R.id.normalButton);
        Button btn_hard = (Button) findViewById(R.id.hardButton);
        btn_easy.setOnClickListener(this);
        btn_normal.setOnClickListener(this);
        btn_hard.setOnClickListener(this);

        intent = new Intent(OnlineActivity.this, GameActivity.class);
    }

    public void onClick(View v) {
        TextView waiting = findViewById(R.id.textView);
        waiting.bringToFront();
        waiting.setVisibility(View.VISIBLE);
        if(v.getId() == R.id.easyButton) {
//            builder.setDismissMessage();
            new Thread(){
                @Override
                public void run(){
                    Log.i(TAG, "send message to server");
                    writer.println("1");
                }
            }.start();
            intent.putExtra("gameType", 1);
        } else if(v.getId() == R.id.normalButton) {
            new Thread(){
                @Override
                public void run(){
                    Log.i(TAG, "send message to server");
                    writer.println("2");
                }
            }.start();
            intent.putExtra("gameType", 2);
        } else if(v.getId() == R.id.hardButton) {
            new Thread(){
                @Override
                public void run(){
                    Log.i(TAG, "send message to server");
                    writer.println("3");
                }
            }.start();
            intent.putExtra("gameType", 3);
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
//                socket = ;
                socket = new Socket();
                ((MySocket)getApplication()).setSocket(socket);
//                MySocket mySocket = new MySocket(socket);
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

                        try{
                            while((fromserver = in.readLine())!=null)
                            {
                                System.out.println(fromserver);
                                if (Objects.equals(fromserver, "fail")) {
                                    MainActivity.activityManager.finishActivity(OnlineActivity.this);
                                    System.out.println("faileddd");
                                    break;
                                }else if(Objects.equals(fromserver, "success")){
                                    System.out.println("ffff");
                                    intent.putExtra("music", music);
                                    intent.putExtra("gameMode", 1);
                                    startActivity(intent);
                                    MainActivity.activityManager.finishActivity(OnlineActivity.this);
                                }else{
                                    Message msg = Message.obtain();
                                    msg.what = 4;
                                    msg.obj = fromserver;
                                    System.out.println(fromserver);
                                    GameActivity.mHandler.sendMessage(msg);
                                }
                                //发送消息给UI线程    发送分数

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
