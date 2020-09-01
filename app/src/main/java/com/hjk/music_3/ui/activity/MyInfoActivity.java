package com.hjk.music_3.ui.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.ActivityMyinfoBinding;
import com.hjk.music_3.databinding.ActivitySettingBinding;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

public class MyInfoActivity extends AppCompatActivity {

    ActivityMyinfoBinding binding;
    UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_myinfo);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();
        binding.setUser(userViewModel);
    }



}
