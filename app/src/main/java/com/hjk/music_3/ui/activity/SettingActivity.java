package com.hjk.music_3.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    Intent intent=getIntent();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_setting);
        binding.setActivity(this);

    }

    public void Intent_MyInfo(){
        intent=new Intent(SettingActivity.this,MyInfoActivity.class);
        startActivity(intent);
    }

    public void Intent_PassChange(){
        intent=new Intent(SettingActivity.this,PassChangeActivity.class);
        startActivity(intent);
    }

    public void Intent_LogOut(){
        intent=new Intent(SettingActivity.this,PopUpActivity.class);
        startActivity(intent);
    }


    public void Intent_Alarm(){
        intent=new Intent(SettingActivity.this,AlarmActivity.class);
        startActivity(intent);
    }


}
