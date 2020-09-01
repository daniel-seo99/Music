package com.hjk.music_3.data.local.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {
    private static Gson gson=new Gson();

    @TypeConverter
    public static String fromMusicList(List<Music> musics){
        return gson.toJson(musics);
    }

    @TypeConverter
    public  static List<Music> toMusicList(String musics){
        if(musics==null){
            return Collections.emptyList();
        }

        Type listType=new TypeToken<List<Music>>() {}.getType();

        return gson.fromJson(musics,listType);
    }

}
