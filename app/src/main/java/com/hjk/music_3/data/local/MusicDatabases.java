package com.hjk.music_3.data.local;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hjk.music_3.data.local.dao.MusicDao;
import com.hjk.music_3.data.local.dao.UserDao;
import com.hjk.music_3.data.local.model.Converters;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.local.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Room Database that manages a local database.
 *
 * @author Yassin Ajdi.
 */
@Database(
        entities = {Music.class},
        version = 1,
        exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MusicDatabases extends RoomDatabase {

    public static final String DATABASE_NAME = "Movies.db";

    private static MusicDatabases INSTANCE;

    private static final Object sLock = new Object();

    private static final int NUMBER_OF_THREADS=4;

     public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract MusicDao musicDao();


    public static MusicDatabases getInstance(Context context) {
            if (INSTANCE == null) {
                synchronized (MusicDatabases.class) {

                    if(INSTANCE==null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                MusicDatabases.class,"music_database")
                                .addCallback(sRoomDatabaseCallback)
                                .allowMainThreadQueries()
                                .build();
                    }
                }
            }
            return INSTANCE;
    }



    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


            databaseWriteExecutor.execute(() -> {

            });
        }
    };
}
