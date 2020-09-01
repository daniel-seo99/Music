package com.hjk.music_3.utils;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class SnackbarMessage extends SingleLiveEvent<Integer> {

    public void observe(LifecycleOwner owner, final SnakbarObserver observer){
        super.observe(owner,new Observer<Integer>(){
            @Override
            public void onChanged(@Nullable Integer t){
                if(t==null){
                    return;
                }
                observer.onNewMessage(t);
            }
        });
    }

    public interface SnakbarObserver{
        void onNewMessage(@StringRes int snackbarMessageResourceId);
    }
}
