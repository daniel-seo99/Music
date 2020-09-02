package com.hjk.music_3.ui.viewmodel;

import android.app.Application;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.remote.MusicRepository;

import java.util.List;

public class MusicViewModel extends AndroidViewModel {

    private LiveData<List<Music>> AllMusic;

    private static MutableLiveData<List<Music>> music;
    private static MutableLiveData<Music> current_music=new MutableLiveData<Music>();
    private static MusicRepository musicRepository=MusicRepository.getInstance();
    private static MusicRepository musicRepository_local;
    public static MutableLiveData<Boolean> isPlaying=new MutableLiveData<Boolean>();
    public static boolean change=true;
    public static int pos=1;
    public static int pos_=1;

    public static MutableLiveData<String> Progress=new MutableLiveData<String>();

    public final ObservableField<String> maxSeekDuration = new ObservableField<String>();

    public final ObservableInt currentSeekPosition = new ObservableInt();




    public MusicViewModel(Application application){
        super(application);
        musicRepository_local=new MusicRepository(application);
        AllMusic=musicRepository_local.getAllMusic();

    }


    public void init(){
//       if(change==true){
//           pos_=UserViewModel.load_save_music();
//           change=false;
//           isPlaying.setValue(false);
//      }

      if(music!=null)
         return;
       music=musicRepository.getMusic();

    }

    //Room
    public LiveData<List<Music>> getAllMusic() {
        return AllMusic;
    }

    public static void insert(List<Music> music){musicRepository_local.insert(music);}

    public int getSize(){ return music.getValue().size();}


    //Retrofit
    public MutableLiveData<List<Music>> getMusic(){
        return music;
    }


    //method
    public static MutableLiveData<Music> current_music(){
        return current_music;
    }

    public void set_current_music(int pos){
        if(pos==9999){
            Music music=new Music();
            music.setTitle("음악을 추가해주세요");
            music.setImage("https://i.pinimg.com/originals/f7/3a/5b/f73a5b4b7262440684a2b5c39e684304.jpg");
            current_music.setValue(music);
            return;
        }
        current_music.setValue(getMusic().getValue().get(pos));
    }

    public int getPos(){
        return this.pos;
    }

    public void setPos(int pos){
        this.pos= pos;
        UserViewModel.save_music(pos); //사용자 가장 마지막 재생한 음악
    }

    public void setIsPlaying(boolean isPlaying){this.isPlaying.setValue(isPlaying);}

    public static MutableLiveData<Boolean> getIsPlaying(){return isPlaying;}

    public void setProgress(String progress){
        this.Progress.setValue(progress);
    }

    public MutableLiveData<String> getProgress(){
        return this.Progress;
    }

    public void setChange(boolean change){this.change=change;}

    public boolean getChange(){return this.change;}


}
