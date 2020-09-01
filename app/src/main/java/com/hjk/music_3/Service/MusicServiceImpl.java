package com.hjk.music_3.Service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.Player1Binding;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;

public class MusicServiceImpl extends AppCompatActivity {
    private static ServiceConnection serviceConnection;
    private static MusicService musicService;
    Player1Binding binding;

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        binding=DataBindingUtil.setContentView(this, R.layout.player_1);

    }

    public MusicServiceImpl(Context context){

        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                musicService=((MusicService.MusicServiceBinder)service).getService();

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                serviceConnection=null;
                musicService=null;
            }
        };
        context.bindService(new Intent(context,MusicService.class).setPackage(context.getPackageName()),serviceConnection,Context.BIND_AUTO_CREATE);
    }

    public void setViewModel(MusicViewModel musicViewModel){
        musicService.setViewModel(musicViewModel);
    }

    public void setFragment(Fragment fragment) { musicService.getFragment(fragment);}

    public void play() throws Exception{
        musicService.play();
    }

    public void next() {
        musicService.next();
    }

    public void prev(){
        musicService.prev();
    }

    public void start(){
        musicService.start();
    }

    public void pause(){
        musicService.pause();
    }

    public int getDuration(){
        return musicService.getDuration();
    }

    public int current_position(){
        return musicService.current_position();
    }



    public void setData(){musicService.setData();}

    public boolean isPlaying(){return musicService.isPlaying();}
}
