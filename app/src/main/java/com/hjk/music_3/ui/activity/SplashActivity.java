package com.hjk.music_3.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.hjk.music_3.R;
import com.hjk.music_3.ui.viewmodel.BackViewModel;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

public class SplashActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    BackViewModel backViewModel;
    MusicViewModel musicViewModel;

    Intent intent=getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();

        backViewModel=ViewModelProviders.of(this).get(BackViewModel.class);
        backViewModel.init();

        backViewModel.getBack().observe(this,b->{
            int pos_;
            if(UserViewModel.load_save_back()!=0){
                pos_=UserViewModel.load_save_back();
            }
            else{
                pos_=0;
            }
            backViewModel.set_current_back(b.get(pos_));
        });

        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        musicViewModel.init();
        musicViewModel.getMusic().observe(this,m->{
            int pos_;
            if(UserViewModel.load_save_music()!=0){
                pos_=UserViewModel.load_save_back();
            }
            else{
                pos_=0;
            }
            musicViewModel.set_current_music(m.get(pos_));
        });

        String Email="hjk";
        String Pwd="123";
        String Pwd2="123";
        userViewModel.getLogin(Email).observe(this, userViewModel->{

        });

        Handler handler=new Handler();
        handler.postDelayed(new splash(),3000);

    }

    private class splash implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), MusicActivity.class));
            finish();
        }
    }
}
