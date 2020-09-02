package com.hjk.music_3.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.Player1Binding;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.utils.Binding;

public class PlayerActivity extends AppCompatActivity {

    Player1Binding binding;
    MusicViewModel musicViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);

        binding= DataBindingUtil.setContentView(this, R.layout.player_1);
        binding.setMusic(musicViewModel);
        binding.setActivity(this);


        Intent intent=getIntent();
        int bno=intent.getIntExtra("bno",999999);

        if(bno==musicViewModel.getPos()) {

        }
        else{
            MusicApplication.getInstance().getServiceInterface().setData();

        }
        //하단바 클릭시 재생중인 노래면 다시 재생x

    }

    @Override
    public void onResume(){
        super.onResume();
        musicViewModel.current_music().observe(this,new Observer<Music>(){
            @Override
            public void onChanged(Music music){
                binding.titleText.setText(music.getTitle());
                Binding.PicassoImage(binding.background, music.getImage());
            }
        });

        musicViewModel.getProgress().observe(this,new Observer<String>(){
            @Override
            public void onChanged(String string){
                binding.musicTime.setText(string);
            }
        });

        musicViewModel.getIsPlaying().observe(this,new Observer<Boolean>(){
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.playBtn.setImageResource(R.drawable.ic_pause_48dp);
                }else{
                    binding.playBtn.setImageResource(R.drawable.ic_play_arrow_48dp);
                }
            }
        });

    }

    public void next()   {
        MusicApplication.getInstance().getServiceInterface().next();

    }

    public void prev(){
        MusicApplication.getInstance().getServiceInterface().prev();

    }

    public void start_pause(){
        if(MusicApplication.getInstance().getServiceInterface().isPlaying()){
            MusicApplication.getInstance().getServiceInterface().pause();

        }
        else{
            MusicApplication.getInstance().getServiceInterface().start();

        }
    }


}
