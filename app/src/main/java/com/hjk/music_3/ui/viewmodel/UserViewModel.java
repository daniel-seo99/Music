package com.hjk.music_3.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.data.remote.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private static MutableLiveData<User> user=null;
    private static UserRepository userRepository_local;
    private UserRepository userRepository;

    private LiveData<User> local_user;
    private static int save_music=99999;
    private static int save_back=99999;

    public UserViewModel(Application application){
        super(application);
        userRepository_local=new UserRepository(application);
        local_user=userRepository_local.get_local_user();

        save_music=userRepository_local.load_save_music();
        if(save_music==0){ save_music=save_music+1; }
        save_back=userRepository_local.load_save_back();
        if(save_back==0){ save_back=save_back+1; }

    }

    public static void insert_user(User user){userRepository_local.insert_user(user);}

    public static void save_back(int save_back){userRepository_local.save_back(save_back);}

    public static int load_save_back(){return save_back;}

    public static void save_music(int save_music){userRepository_local.save_music(save_music);}

    public static int load_save_music(){return save_music;}

    public void init(){
        if(user!=null)
            return;
        userRepository=UserRepository.getInstance();
    }

    public LiveData<User> getLogin(String id){
        user=userRepository.getLogin(id);
        return user;
    }

    public static MutableLiveData<User> getUser(){
        return user;

    }


}
