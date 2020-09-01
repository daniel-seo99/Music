package com.hjk.music_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.android.material.tabs.TabLayout;
import com.hjk.music_3.ui.adapter.FragmentAdapter;
import com.hjk.music_3.ui.adapter.MyPagerAdapter;
import com.hjk.music_3.ui.fragment.DreamFragment;
import com.hjk.music_3.ui.fragment.MusicFragment;
import com.hjk.music_3.ui.fragment.ProfileFragment;
import com.hjk.music_3.ui.fragment.ScapeFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

public class MainActivity extends Fragment {



    public static MainActivity newInstance(){return new MainActivity();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.sample2,container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        TabLayout tabs = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("전체"));
        tabs.addTab(tabs.newTab().setText("수면"));
        tabs.addTab(tabs.newTab().setText("마음"));


        tabs.setTabGravity(tabs.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        final MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getFragmentManager(), 3);
        viewPager.setAdapter(myPagerAdapter);

        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));


    }
}