package com.hjk.music_3.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hjk.music_3.R;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.ItemMusicContentBinding;

import java.util.List;

public class MusicContentAdapter extends RecyclerView.Adapter<MusicContentAdapter.ItemViewHolder> {

    private List<Music> music;
    private Context context;
    MusicContentAdapter.OnItemClickListener mOnItemClickListener;

    public MusicContentAdapter(List<Music> music, OnItemClickListener itemClickListener){
        this.music=music;
        this.mOnItemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public MusicContentAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        ItemMusicContentBinding itemMusicContentBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_music_content,parent,false);
        return new MusicContentAdapter.ItemViewHolder(itemMusicContentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicContentAdapter.ItemViewHolder holder, int pos){
        holder.bind(pos);
    }

    @Override
    public int getItemCount(){
        if(music==null){
            return 0;
        }
        return music.size();
    }


    public interface OnItemClickListener{
        void onItemClicked(int pos, LinearLayout linearLayout) throws Exception;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemMusicContentBinding itemMusicContentBinding;
        LinearLayout event;

        ItemViewHolder(@NonNull ItemMusicContentBinding itemView){
            super(itemView.getRoot());
            itemMusicContentBinding=itemView;
            itemView.getRoot().setOnClickListener(this);
            event=itemView.getRoot().findViewById(R.id.event);
        }

        void bind(int pos){
            Music music_item=music.get(pos);
            itemMusicContentBinding.setMusic(music_item);
        }

        @Override
        public void onClick(View v){
            try{
                mOnItemClickListener.onItemClicked(getAdapterPosition(),event);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
