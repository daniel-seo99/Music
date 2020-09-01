package com.hjk.music_3.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjk.music_3.MainActivity;
import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.Service.MusicService;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.ActivityMainBinding;
import com.hjk.music_3.ui.fragment.DreamFragment;
import com.hjk.music_3.ui.fragment.MusicFragment;
import com.hjk.music_3.ui.fragment.ProfileFragment;
import com.hjk.music_3.ui.fragment.ScapeFragment;
import com.hjk.music_3.ui.fragment.SleepFragment;

import com.hjk.music_3.ui.viewmodel.BackViewModel;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.utils.ActivityUtils;
import com.hjk.music_3.utils.Binding;
import com.hjk.music_3.utils.SecondUtils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.concurrent.TimeUnit;

public class MusicActivity extends AppCompatActivity {

    MusicViewModel musicViewModel;
    ActivityMainBinding binding;
    public static int time;
    public static int time2;
    public static int t;
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setupViewFragment();


        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        binding.setActivity(this);






        setupBottomNavigation();

    }

    @Override
    public void onResume(){
        super.onResume();

        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setActivity(this);

        if(MusicApplication.getInstance().getServiceInterface().isPlaying()){
            binding.playBtn.setImageResource(R.drawable.ic_pause_48dp);

        }else{
            binding.playBtn.setImageResource(R.drawable.ic_play_arrow_48dp);
        }

        setupViewFragment();
        setupBottomNavigation();


        //뮤직 선택후 다시 UI업데이트를 위해
    }

    final Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0:
                    if(musicViewModel.getChange()){
                        UI();
                    }

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
                    binding.musicTime.setText(music_time+"/"+all_music_time);
                    musicViewModel.setChange(false);
                    break;
            }
        }
    };

    class Thread extends java.lang.Thread{
        @Override
        public void run(){
            super.run();


            while(musicViewModel.getIsPlaying()){

                int duration=MusicApplication.getInstance().getServiceInterface().getDuration();
                time = Integer.parseInt(SecondUtils.formateMilliSeccond(duration));

                int current_pos=MusicApplication.getInstance().getServiceInterface().current_position();
                time2=Integer.parseInt(SecondUtils.formateMilliSeccond(current_pos));


                Message message=handler.obtainMessage();

                message.what=0;

                String msg=new String(":");

                message.obj=msg;


                message.arg1=time2;
                message.arg2=time;
                System.out.println(message.arg1);

                handler.sendMessage(message);

                try{
                    sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

        }
    }


    public void Intent_Current_Music(){
        Intent intent=new Intent(MusicActivity.this, PlayerActivity.class);
        intent.putExtra("bno",musicViewModel.getPos());
        startActivity(intent);
    }

    public void UI(){
        musicViewModel.getMusic().observe(this,m->{
            binding.musicTitle.setText(m.get(musicViewModel.getPos()).getTitle());
            Binding.PicassoImage(binding.musicImage, m.get(musicViewModel.getPos()).getImage());
        });


        binding.playBtn.setImageResource(R.drawable.ic_pause_48dp);

        getTime();
        //다음 재생시 시간 표시 이상하게됨, 시간 표시 문제 해결하기
    }

    public void getTime(){
        System.out.println("쓰레드 실행 여부"+musicViewModel.getIsPlaying());
        if(musicViewModel.getIsPlaying()) {
            thread = new Thread();
            thread.start();
        }

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

    public void next()   {
        MusicApplication.getInstance().getServiceInterface().next();
        UI();
    }

    public void prev(){
        MusicApplication.getInstance().getServiceInterface().prev();
        UI();
    }

    private void setupViewFragment(){
        MusicFragment musicFragment=MusicFragment.newInstance();
        ActivityUtils.replaceFragmentInActivity(
                getSupportFragmentManager(),musicFragment,R.id.fragment_container);
    }


    private void setupBottomNavigation(){
        BottomNavigationView bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action1:
                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(),MusicFragment.newInstance(),
                                R.id.fragment_container);
                        return true;

                    case R.id.action2:
                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), MainActivity.newInstance(),
                                R.id.fragment_container);
                        return true;


                    case R.id.action3:
                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), SleepFragment.newInstance(),
                                R.id.fragment_container);
                        return true;

                    case R.id.action4:
                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), ScapeFragment.newInstance(),
                                R.id.fragment_container);
                        return true;


                    case R.id.action5:

                        ActivityUtils.replaceFragmentInActivity(
                                getSupportFragmentManager(), ProfileFragment.newInstance(),
                                R.id.fragment_container);
                        return true;
                }
                return false;
            }
        });
    }



}

//2020 08 25
//첫번째 프래그먼트와 3번째 프래그먼트 안에 액티비티 값을 일치시켰다
// java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String 미해결
//브로드 캐스트 작업,
// 노래재생중에 내 어플에 재생시 다른 어플 음악 중지하기 개발하기
// 스와이프해서 화면 내리기 개발하기, 이어폰 뺄 때 노래정지 개발하기