package com.hjk.music_3.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.FragmentDreamBinding;
import com.hjk.music_3.ui.activity.PlayerActivity;
import com.hjk.music_3.ui.adapter.FragmentAdapter;
import com.hjk.music_3.ui.adapter.MusicAdapter;
import com.hjk.music_3.ui.adapter.MusicAdapter2;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.utils.ItemOffDecoration;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

public class DreamFragment extends Fragment implements MusicAdapter2.OnItemClickListener{

    MusicAdapter2 musicAdapter;

    SwipeRefreshLayout refreshLayout;

    FragmentDreamBinding binding;

    MusicViewModel musicViewModel;



    public static DreamFragment newInstance(){return new DreamFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        setHasOptionsMenu(true);

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_dream,container,false);

        View root=binding.getRoot();




        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        getData();
    }




    public void getData(){
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);

        musicViewModel.getMusic().observe(this,musicViewModel->{
           if(musicViewModel!=null){
               setMusic(musicViewModel);
           }
        });
    }

    public void setMusic(List<Music> m){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int pos) {
                return 1;
            }
        });

        RecyclerView recyclerView_music_list=getActivity().findViewById(R.id.music_list);
        musicAdapter=new MusicAdapter2(m,this);

        recyclerView_music_list.setAdapter(musicAdapter);
        recyclerView_music_list.setLayoutManager(gridLayoutManager);
        ItemOffDecoration itemOffDecoration=new ItemOffDecoration(getActivity(),R.dimen.item_offset);
        recyclerView_music_list.addItemDecoration(itemOffDecoration);
    }

    @Override
    public void onItemClicked(int pos, ImageView imageView) throws Exception{
        final int pos_=pos;

        musicViewModel.getMusic().observe(this,m->{
            musicViewModel.set_current_music(m.get(pos_));
        });
        MusicApplication.getInstance().getServiceInterface().setData();
        Intent intent=new Intent(getActivity(), PlayerActivity.class);
        startActivity(intent);
    }
}
