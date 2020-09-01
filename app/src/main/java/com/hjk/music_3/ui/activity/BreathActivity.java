package com.hjk.music_3.ui.activity;

import android.database.DatabaseUtils;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.ActivityBreathBinding;
import com.hjk.music_3.ui.viewmodel.BackViewModel;
import com.hjk.music_3.utils.ToastUtils;

public class BreathActivity extends AppCompatActivity {

    ActivityBreathBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_breath);
        binding.setActivity(this);
        binding.Minute.setMinValue(1);
        binding.Minute.setMaxValue(60);
        binding.Minute.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(binding.Minute,android.R.color.white);
        binding.Minute.setWrapSelectorWheel(false);
        binding.Minute.setValue(30);
        binding.Minute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {

            }
        });
    }

    private void setDividerColor(NumberPicker picker,int color){
        java.lang.reflect.Field[] pickerFields=NumberPicker.class.getDeclaredFields();
        for(java.lang.reflect.Field pf : pickerFields){
            if(pf.getName().equals("mSelectionDivider")){
                pf.setAccessible(true);
                try{
                    ColorDrawable colorDrawable=new ColorDrawable(color);
                    pf.set(picker,colorDrawable);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
