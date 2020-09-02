package com.hjk.music_3.Service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.MutableLiveData;

import com.hjk.music_3.Receiver.CommandActions;
import com.hjk.music_3.Receiver.MusicReceiver;
import com.hjk.music_3.Receiver.NotificationPlayer;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.ui.activity.MusicActivity;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.SecondUtils;

import java.util.ArrayList;

public class MusicService extends LifecycleService  {

    private final IBinder binder=new MusicServiceBinder();
    public static MediaPlayer mediaPlayer;

    boolean isPlaying=true;

    static Uri url;
    static MusicViewModel musicViewModel;
    static Music current_music;
    public static Fragment fragment;
    IntentFilter intentFilter;

    MusicReceiver myNoisyAudioStreamReceiver;
    private NotificationPlayer mNotificationPlayer;
    private PlayerCallHelper mPlayerCallHelper;

    Thread thread;


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

        intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        myNoisyAudioStreamReceiver = new MusicReceiver();

        mNotificationPlayer = new NotificationPlayer(this);


        if (mPlayerCallHelper == null) {
            mPlayerCallHelper = new PlayerCallHelper(new PlayerCallHelper.PlayerCallHelperListener() {
                @Override
                public void playAudio() {
                    MusicApplication.getInstance().getServiceInterface().start();
                }

                @Override
                public boolean isPlaying() {
                    return MusicApplication.getInstance().getServiceInterface().isPlaying();
                }

                @Override
                public void pauseAudio() {
                    MusicApplication.getInstance().getServiceInterface().pause();
                }
            });
        }
        mPlayerCallHelper.bindCallListener(getApplicationContext());
        mPlayerCallHelper.bindRemoteController(getApplicationContext());


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                updateNotificationPlayer();

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
                    updateNotificationPlayer();
                    musicViewModel.set_current_music(musicViewModel.getPos());

                    setData();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                updateNotificationPlayer();

                return false;
            }
        });

        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mediaPlayer) {
                updateNotificationPlayer();

            }
        });


    }

    public void setData() {

        final int pos_=musicViewModel.getPos();
        UserViewModel.save_music(pos_); //노래저장


        url = Uri.parse(musicViewModel.current_music().getValue().getMp3());
        musicViewModel.setIsPlaying(true);
        play();

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mPlayerCallHelper.unbindCallListener(getApplicationContext());
            mPlayerCallHelper.unbindRemoteController();
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

        mPlayerCallHelper.requestAudioFocus("aa","bb");
        registerReceiver(myNoisyAudioStreamReceiver, intentFilter);

        if(musicViewModel.getIsPlaying().getValue()){
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

            mediaPlayer.start();
            thread_start();
            musicViewModel.setIsPlaying(true);
            updateNotificationPlayer();
        }



    }

    public void thread_start(){

        thread=new MusicService.Thread();
        thread.start();
    }

    public void next()  {
        if(musicViewModel.getSize()-1<musicViewModel.getPos()+1){
            musicViewModel.setPos(0);

        }else {
            musicViewModel.setPos(musicViewModel.getPos() + 1);
        }
        musicViewModel.set_current_music(musicViewModel.getPos());
        setData();

    }

    public void prev(){
        if(musicViewModel.getPos()-1<0){
            musicViewModel.setPos(musicViewModel.getSize()-1);
        }else {
            musicViewModel.setPos(musicViewModel.getPos() - 1);
        }
        musicViewModel.set_current_music(musicViewModel.getPos());
        setData();
    }


    public void start(){
        musicViewModel.setIsPlaying(true);
        updateNotificationPlayer();
        mPlayerCallHelper.requestAudioFocus("aa","bb");
        mediaPlayer.start();
        thread_start();
    }

    public void pause(){

        musicViewModel.setIsPlaying(false);
        updateNotificationPlayer();
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

    @Override
    public  int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (NotificationPlayer.CommandActions.TOGGLE_PLAY.equals(action)) {
                if (musicViewModel.getIsPlaying().getValue()) {
                    pause();
                } else {
                    start();

                }
            } else if (NotificationPlayer.CommandActions.REWIND.equals(action)) {
                prev();
            } else if (NotificationPlayer.CommandActions.FORWARD.equals(action)) {
                next();
            } else if (NotificationPlayer.CommandActions.CLOSE.equals(action)) {
                pause();
                removeNotificationPlayer();
            }
        }

        if (mPlayerCallHelper == null) {
            mPlayerCallHelper = new PlayerCallHelper(new PlayerCallHelper.PlayerCallHelperListener() {
                @Override
                public void playAudio() {
                    MusicApplication.getInstance().getServiceInterface().start();
                }

                @Override
                public boolean isPlaying() {
                    return MusicApplication.getInstance().getServiceInterface().isPlaying();
                }

                @Override
                public void pauseAudio() {
                    MusicApplication.getInstance().getServiceInterface().pause();
                }
            });
        }
        mPlayerCallHelper.bindCallListener(getApplicationContext());


        return super.onStartCommand(intent, flags, startId);

    }


    private void updateNotificationPlayer() {
        if (mNotificationPlayer != null) {
            mNotificationPlayer.updateNotificationPlayer();
        }
    }

    private void removeNotificationPlayer() {
        if (mNotificationPlayer != null) {
            mNotificationPlayer.removeNotificationPlayer();
        }
    }

    final Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0:

                    String music_time=Integer.toString(msg.arg1);
                    String all_music_time=Integer.toString(msg.arg2);
                    if(music_time.length()>=3) {
                        music_time = music_time.substring(0, 1) + ":" + music_time.substring(1);
                    }
                    else{
                        music_time="0:"+music_time.substring(0);
                    }
                    if(all_music_time.length()>=3) {
                        all_music_time = all_music_time.substring(0, 1) + ":" + all_music_time.substring(1);
                    }
                    else{
                        all_music_time="0:"+all_music_time.substring(0);
                    }
                    String progress=music_time+"/"+all_music_time;
                    musicViewModel.setProgress(progress);
                    break;
            }
        }
    };

    class Thread extends java.lang.Thread{
        @Override
        public void run(){
            super.run();


            while(musicViewModel.getIsPlaying().getValue()){

                int duration=getDuration();
                int All_Time = Integer.parseInt(SecondUtils.formateMilliSeccond(duration));

                int current_pos=current_position();
                int current_progress=Integer.parseInt(SecondUtils.formateMilliSeccond(current_pos));


                Message message=handler.obtainMessage();

                message.what=0;

                String msg=new String(":");

                message.obj=msg;


                message.arg1=current_progress;
                message.arg2=All_Time;



                handler.sendMessage(message);

                try{
                    sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

        }
    }


    // 1. 멈춤하고 다음 재생을 하면 기존곡으로 설정됨
    // 2. 시간표시에 관한 문제
    // 3.
}
