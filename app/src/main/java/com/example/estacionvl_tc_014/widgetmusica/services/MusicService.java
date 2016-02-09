package com.example.estacionvl_tc_014.widgetmusica.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {

    public static final String ACTION_PLAY="musicservice.play";
    public static final String ACTION_PAUSE="musicservice.pause";
    public static final String ACTION_STOP="musicservice.stop";

    MediaPlayer player;
    boolean paused = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction().equals(ACTION_PLAY)){
            playMusic();
        }else if(intent.getAction().equals(ACTION_PAUSE)){
            pauseMusic();
        }else{
            stopMusic();
        }
        return START_NOT_STICKY;
    }

    private void playMusic() {
        if(player == null){
            initMediaPlayer();
            loadSong("");
            setForeground();
        }else{
            if(paused)
                player.start();
        }
        paused = false;
    }

    private void initMediaPlayer() {

    }

    private void loadSong(String song) {

    }

    private void setForeground() {

    }

    private void pauseMusic() {
        if(player!=null){
            player.pause();
            paused = true;
        }
    }

    private void stopMusic() {
        if(player!=null){
            player.stop();
            stopSelf();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}
