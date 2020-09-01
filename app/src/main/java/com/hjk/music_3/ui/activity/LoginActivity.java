package com.hjk.music_3.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;


import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.databinding.ActivityLoginBinding;
import com.hjk.music_3.ui.viewmodel.BackViewModel;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    BackViewModel backViewModel;
    MusicViewModel musicViewModel;

    ActivityLoginBinding binding;
    Intent intent=getIntent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login);
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




        binding.setActivity(this);
    }

    public void Intent_Sign(){
        intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void login(){
        String Email=binding.Email.getText().toString();
        String Pwd=binding.Password.getText().toString();

        userViewModel.getLogin(Email).observe(this, userViewModel->{
           if(userViewModel!=null){
               if(userViewModel.getId().equals(Email) && userViewModel.getPwd().equals(Pwd) ){
                   intent =new Intent(LoginActivity.this,MusicActivity.class);
                   startActivity(intent);
               }
               else{
                   Toast.makeText(this,"아이디 또는 비밀번호가 틀립니다.",Toast.LENGTH_SHORT).show();
               }
           }
        });
    }
}

