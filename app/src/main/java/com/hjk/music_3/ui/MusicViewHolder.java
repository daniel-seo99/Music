package com.hjk.music_3.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.ItemMusicBinding;
import com.hjk.music_3.ui.activity.PlayerActivity;

public class MusicViewHolder extends RecyclerView.ViewHolder {

    private final ItemMusicBinding binding;

    public MusicViewHolder(@NonNull ItemMusicBinding binding){
        super(binding.getRoot());

        this.binding=binding;
    }

    public void bindTo(final Music music){
        binding.setMusic(music);

        binding.getRoot().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(view.getContext(), PlayerActivity.class);
                intent.putExtra(PlayerActivity.EXTRA_MUSIC_ID,music.getBno());
                view.getContext().startActivity(intent);

            }
        });

        binding.executePendingBindings();
    }

    public static MusicViewHolder crate(ViewGroup parent){
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        ItemMusicBinding binding=
                ItemMusicBinding.inflate(layoutInflater,parent,false);
        return new MusicViewHolder(binding);
    }


}
