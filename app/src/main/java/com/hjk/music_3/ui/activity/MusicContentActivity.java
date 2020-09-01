package com.hjk.music_3.ui.activity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.ActivityCategoryContentBinding;
import com.hjk.music_3.databinding.ItemMusicContentBinding;
import com.hjk.music_3.ui.adapter.MusicContentAdapter;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.utils.ItemOffDecoration;

import java.util.List;

public class MusicContentActivity extends AppCompatActivity implements MusicContentAdapter.OnItemClickListener{

    MusicViewModel musicViewModel;
    ActivityCategoryContentBinding binding;
    ItemMusicContentBinding itemMusicContentBinding;
    MusicContentAdapter musicContentAdapter;


    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_category_content);
        binding.setActivity(this);

        getData();
    }

    @Override
    public void onResume(){
        super.onResume();
        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_category_content);
        binding.setActivity(this);

        getData();

//        musicViewModel.current_music().observe(this, new Observer<Music>() {
//            @Override
//            public void onChanged(Music music) {
//                musicViewModel.current_music();
//                System.out.println(musicViewModel.current_music().getValue().getTitle()+"dd");
//            }
//        });


    }


    public void getData(){
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);

        musicViewModel.getMusic().observe(this,m->{
            if(m!=null){
                setMusic(m);

            }
        });
    }

    public void setMusic(List<Music> m){

        RecyclerView recycler_music_list=binding.content;
        musicContentAdapter=new MusicContentAdapter(m,this);

        recycler_music_list.setAdapter(musicContentAdapter);
        recycler_music_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void onItemClicked(int pos, LinearLayout linearLayout) throws Exception{
        final int pos_=pos;
        musicViewModel.getMusic().observe(this,m->{
            musicViewModel.set_current_music(m.get(pos_));
        });
        MusicApplication.getInstance().getServiceInterface().setData();
        Intent intent=new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }
}

