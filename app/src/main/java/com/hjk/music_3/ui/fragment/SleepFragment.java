package com.hjk.music_3.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.FragmentSleepBinding;
import com.hjk.music_3.ui.activity.MusicContentActivity;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;

import org.jetbrains.annotations.Nullable;

public class SleepFragment extends Fragment {

    private MusicViewModel musicViewModel;

    FragmentSleepBinding binding;

    public static SleepFragment newInstance(){return new SleepFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        setHasOptionsMenu(true);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_sleep,container,false);
        View root=binding.getRoot();
        binding.setActivity(this);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public void Intent_Peace(){
        Intent intent=new Intent(getActivity(), MusicContentActivity.class);
        startActivity(intent);

    }

}
