package com.hjk.music_3.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.FragmentMusicBinding;
import com.hjk.music_3.ui.activity.PlayerActivity;
import com.hjk.music_3.ui.adapter.MusicAdapter;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;


import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MusicFragment extends Fragment implements MusicAdapter.OnItemClickListener{

    private MusicViewModel musicViewModel;

    private UserViewModel userViewModel;

    List<Music> music;

    MusicAdapter musicAdapter2;

    SwipeRefreshLayout refreshLayout;

    FragmentMusicBinding binding;

    public static MusicFragment newInstance(){return new MusicFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        userViewModel=ViewModelProviders.of(this).get(UserViewModel.class);

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_music,container,false);
        View root=binding.getRoot();
        binding.setTest(userViewModel);

        return root;

    }

     @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
//        refreshLayout=getActivity().findViewById(R.id.main_refresh);
//        refreshLayout.setOnRefreshListener(this::getData);
        getData();
    }



    public void getData(){
        musicViewModel =ViewModelProviders.of(this).get(MusicViewModel.class);

        musicViewModel.getMusic().observe(this, m->{
           if(m!=null){
               setMusic(m);
               setMusic2(m);
               setMusic3(m);
               MusicViewModel.insert(m);
               MusicApplication.getInstance().getServiceInterface().setViewModel( ViewModelProviders.of(this).get(MusicViewModel.class));

               music=m;
           }

        });

    }

    public void setMusic(List<Music> m){
        RecyclerView recycler_music_lsit=getActivity().findViewById(R.id.music_list);

        musicAdapter2=new MusicAdapter(m,this);
        recycler_music_lsit.setAdapter(musicAdapter2);

        recycler_music_lsit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

    }

    public void setMusic2(List<Music> m){
        RecyclerView recycler_music_lsit=getActivity().findViewById(R.id.music_list2);

        musicAdapter2=new MusicAdapter(m,this);
        recycler_music_lsit.setAdapter(musicAdapter2);
        recycler_music_lsit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


    }

    public void setMusic3(List<Music> m){
        RecyclerView recycler_music_lsit=getActivity().findViewById(R.id.music_list3);

        musicAdapter2=new MusicAdapter(m,this);
        recycler_music_lsit.setAdapter(musicAdapter2);

        recycler_music_lsit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


    }


    @Override
    public void onItemClicked(int pos, ImageView imageView) throws Exception {

        final int pos_=pos;
        musicViewModel.setPos(pos_);

        musicViewModel.getMusic().observe(this,m->{
            musicViewModel.set_current_music(m.get(pos_));
        });

        MusicApplication.getInstance().getServiceInterface().setData();
        Intent intent=new Intent(getActivity(), PlayerActivity.class);
        startActivity(intent);
    }


}
