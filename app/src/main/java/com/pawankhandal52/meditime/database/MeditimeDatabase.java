package com.pawankhandal52.meditime.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.pawankhandal52.meditime.models.Medicine;

/**
 * Created by Pawan Khandal on 12/12/18,31
 */
@Database(entities = {Medicine.class},version = 1,exportSchema = false)
public abstract class MeditimeDatabase extends RoomDatabase {
    private final static String TAG = MeditimeDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "medicine_database";
    private static MeditimeDatabase sInstance;
    
    public static MeditimeDatabase getInstance(Context context) {
        //TODO Please remove allow main thread line in production
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MeditimeDatabase.class, MeditimeDatabase.DATABASE_NAME)
                        //.allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(TAG, "Getting the database instance ");
        return sInstance;
    }
}
