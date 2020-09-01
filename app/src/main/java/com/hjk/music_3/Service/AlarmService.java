package com.hjk.music_3.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.hjk.music_3.R;

import org.jetbrains.annotations.Nullable;

public class AlarmService extends Service {

    MediaPlayer mediaPlayer;
    int startId;
    boolean Running;

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        if(Build.VERSION.SDK_INT>=26) {
            String CHANNEL_ID = "default";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Alarm", NotificationManager.IMPORTANCE_DEFAULT);


            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification=new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setContentTitle("알림")
                    .setContentText("알림음을 재생합니다.")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();

            startForeground(1,notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String getState = intent.getExtras().getString("state");

        assert getState != null;
        switch (getState) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        // 알람음 재생 X , 알람음 시작 클릭
        if(!this.Running && startId == 1) {

            mediaPlayer = new MediaPlayer();
            Uri url = Uri.parse("https://nowglobalhealing.com/content/uploads/woocommerce_uploads/2020/06/%E1%84%83%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A2%E1%84%8C%E1%85%B5%E1%84%80%E1%85%B5-vxums2.mp3");
            try {
                mediaPlayer.setDataSource(this, url);
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }

            mediaPlayer.start();

            this.Running = true;
            this.startId = 0;
        }


        else if(this.Running && startId == 0) {

            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();

            this.Running = false;
            this.startId = 0;
        }


        else if(!this.Running && startId == 0) {

            this.Running = false;
            this.startId = 0;

        }


        else if(this.Running && startId == 1){

            this.Running = true;
            this.startId = 1;
        }

        else {
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("onDestory() 실행", "서비스 파괴");

    }
}
