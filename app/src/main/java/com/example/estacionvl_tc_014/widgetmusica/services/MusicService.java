package com.example.estacionvl_tc_014.widgetmusica.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

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
            loadSong("revelations.mp3");
            setForeground();
        }else{
            if(paused)
                player.start();
        }
        paused = false;
    }

    private void initMediaPlayer() {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnCompletionListener(this);
        player.setOnPreparedListener(this);
    }

    private void loadSong(String song) {
        try {

            AssetFileDescriptor fD = getAssets().openFd("musica/"+song);

            player.setDataSource(fD.getFileDescriptor(),
                    fD.getStartOffset(), fD.getLength()
                    );

            player.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setForeground() {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Reproduciendo")
                .setContentText("Revelations")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .build();
        startForeground(101,notification);

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

    @Override
    public void onCompletion(MediaPlayer mp) {
        stopSelf();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        player.start();
    }

    @Override
    public void onDestroy() {
        player.release();
        player = null;
        stopForeground(true);
        super.onDestroy();
    }
}
