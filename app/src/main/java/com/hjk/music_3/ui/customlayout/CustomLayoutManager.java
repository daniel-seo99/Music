package com.hjk.music_3.ui.customlayout;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class CustomLayoutManager extends LinearLayoutManager {

    private int mParentWidth;

    public CustomLayoutManager(Context context,int orientation, boolean reverseLayout,int mParentWidth){
        super(context,orientation,reverseLayout);
        this.mParentWidth=mParentWidth;
    }

    @Override
    public int getPaddingLeft() {
        return mParentWidth / 4;
    }

    @Override
    public int getPaddingRight(){
        return getPaddingLeft();
    }

}
