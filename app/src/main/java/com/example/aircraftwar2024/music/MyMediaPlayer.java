package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.aircraftwar2024.R;

public class MyMediaPlayer {

    Context context;
    MediaPlayer bgMP;
    MediaPlayer bossBgmP;

    public MyMediaPlayer(Context context){
        this.context = context;
        bgMP = MediaPlayer.create(context,R.raw.bgm);
        bossBgmP = MediaPlayer.create(context,R.raw.bgm_boss);
    }

    public void playBgm(){
        System.out.printf("bgm should start\n");
        bgMP.start();
        bgMP.setLooping(true);
    }

    public void pauseBgm(){
        bgMP.pause();
    }

    public void pauseBossBgm(){
        bossBgmP.pause();
    }

    public void playBossBgm(){
        bossBgmP.start();
        bossBgmP.setLooping(true);
    }

    public void bgmRestart(){
        bgMP.seekTo(bgMP.getCurrentPosition());
        bgMP.start();
    }

    public void bossBgmRestart(){
        bossBgmP.seekTo(bossBgmP.getCurrentPosition());
        bossBgmP.start();
    }

    public void bgmStop(){
        bgMP.stop();
        bossBgmP.stop();
        bgMP.release();
        bossBgmP.release();
        bgMP = null;
        bossBgmP = null;
    }

}
