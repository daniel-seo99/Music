package com.hjk.music_3.Service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.ui.activity.MusicActivity;
import com.hjk.music_3.ui.activity.PlayerActivity;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.BroadcastActions;
import com.hjk.music_3.utils.SecondUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MusicService extends LifecycleService {

    private final IBinder binder=new MusicServiceBinder();
    public static MediaPlayer mediaPlayer;

    boolean isPlaying=true;
    public static ArrayList<Music> music=new ArrayList<Music>();

    static Uri url;
    static MusicViewModel musicViewModel;
    static Music current_music;
    public static Fragment fragment;


    public class MusicServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return binder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(musicViewModel.getSize()-1<musicViewModel.getPos()+1){
                    musicViewModel.setPos(0);

                }else {
                    musicViewModel.setPos(musicViewModel.getPos() + 1);
                }
                try {
                    setData();
                    musicViewModel.setChange(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });

        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mediaPlayer) {

            }
        });


    }

    public void setData() {

        final int pos_=musicViewModel.getPos();
        UserViewModel.save_music(pos_); //노래저장
        System.out.println(pos_+"dsfdsfsdfs");
        musicViewModel.getMusic().observe(this, m -> {
            musicViewModel.set_current_music(m.get(pos_));
            url = Uri.parse(musicViewModel.current_music().getMp3());
            play();
        });
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    public void setViewModel(MusicViewModel musicViewModel){
        this.musicViewModel=musicViewModel;
    }

    public void getFragment(Fragment fragment){
        this.fragment=fragment;
    }

    public void play()  {
        musicViewModel.setIsPlaying(true);

        if(isPlaying) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            musicViewModel.setIsPlaying(false);
        }

        try{
            mediaPlayer.setDataSource(this,url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
          }catch(Exception e){
            e.printStackTrace();
        }

            if(url!=null) {
                musicViewModel.setIsPlaying(true);

                mediaPlayer.start();
            }
        int duration=MusicApplication.getInstance().getServiceInterface().getDuration();
        int time = Integer.parseInt(SecondUtils.formateMilliSeccond(duration));

        System.out.println("노래시간:"+time);
        System.out.println(mediaPlayer.isPlaying());

    }




    public void next()  {
        if(musicViewModel.getSize()-1<musicViewModel.getPos()+1){
            musicViewModel.setPos(0);

        }else {
            musicViewModel.setPos(musicViewModel.getPos() + 1);
        }

        setData();

    }

    public void prev(){
        if(musicViewModel.getPos()-1<0){
            musicViewModel.setPos(musicViewModel.getSize()-1);
        }else {
            musicViewModel.setPos(musicViewModel.getPos() - 1);
        }

        setData();
    }


    public void start(){
        mediaPlayer.start();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public int current_position(){
        return mediaPlayer.getCurrentPosition();
    }


    // 1. 멈춤하고 다음 재생을 하면 기존곡으로 설정됨
    // 2. 시간표시에 관한 문제
    // 3.
}
