package com.hjk.music_3.ui.activity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.databinding.Player1Binding;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.Binding;
import com.squareup.picasso.Picasso;

public class PlayerActivity extends AppCompatActivity {
    public static final String EXTRA_MUSIC_ID = "extra_movie_id";
    Player1Binding binding;
    MusicViewModel musicViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);
        musicViewModel.init();
        binding= DataBindingUtil.setContentView(this, R.layout.player_1);
        binding.setMusic(musicViewModel);
        binding.setActivity(this);


        Intent intent=getIntent();
        int bno=intent.getIntExtra("bno",999999);

        try {
            binding.playBtn.setImageResource(R.drawable.ic_pause_48dp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void UI(){
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);

        musicViewModel.getMusic().observe(this,m->{
            binding.titleText.setText(m.get(musicViewModel.getPos()).getTitle());
            Binding.PicassoImage(binding.background, m.get(musicViewModel.getPos()).getImage());
        });


        binding.playBtn.setImageResource(R.drawable.ic_pause_48dp);

    }

    public void next()   {
        MusicApplication.getInstance().getServiceInterface().next();
        UI();
    }

    public void prev(){
        MusicApplication.getInstance().getServiceInterface().prev();
        UI();
    }

    public void start_pause(){
        if(MusicApplication.getInstance().getServiceInterface().isPlaying()){
            MusicApplication.getInstance().getServiceInterface().pause();
            binding.playBtn.setImageResource(R.drawable.ic_play_arrow_48dp);

        }
        else{
            MusicApplication.getInstance().getServiceInterface().start();
            binding.playBtn.setImageResource(R.drawable.ic_pause_48dp);

        }
    }


}
