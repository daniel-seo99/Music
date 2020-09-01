package com.hjk.music_3.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.remote.MusicRepository;

import java.util.List;

public class MusicViewModel extends AndroidViewModel {

    private LiveData<List<Music>> AllMusic;

    private static MutableLiveData<List<Music>> music;
    private static Music current_music;
    private static MusicRepository musicRepository=MusicRepository.getInstance();
    private static MusicRepository musicRepository_local;
    public static MutableLiveData<Boolean> isPlaying=new MutableLiveData<Boolean>();
    public static boolean change=true;
    public static int pos=1;
    public static int pos_=1;

    public MusicViewModel(Application application){
        super(application);
        musicRepository_local=new MusicRepository(application);
        AllMusic=musicRepository_local.getAllMusic();
    }


    public void init(){
//        if(change==true){
//            pos_=UserViewModel.load_save_music();
//            change=false;
//            isPlaying.setValue(false);
//        }

        if(music!=null)
            return;
        music=musicRepository.getMusic();

    }

    public LiveData<List<Music>> getAllMusic() {
        return AllMusic;
    }

    public static void insert(List<Music> music){musicRepository_local.insert(music);}

    public MutableLiveData<List<Music>> getMusic(){
        return music;
    }

    public static Music current_music(){
        return current_music;
    }

    public static void set_current_music(Music music){
        current_music=music;
    }


    public int getSize(){ return music.getValue().size();}


    public void setPos(int pos){
        this.pos= pos;
        pos_= (int) getMusic().getValue().get(pos).getBno();
        UserViewModel.save_music(pos_); //사용자 가장 마지막 재생한 음악
    }

    public int getPos(){
        return this.pos;
    }

    public void setIsPlaying(boolean isPlaying){this.isPlaying.setValue(isPlaying);}

    public static Boolean getIsPlaying(){return isPlaying.getValue();}

    public void setChange(boolean change){this.change=change;}

    public boolean getChange(){return this.change;}


}
