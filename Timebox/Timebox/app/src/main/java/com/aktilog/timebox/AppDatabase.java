package com.aktilog.timebox;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

@Database(entities = {Category.class, LoggedActivities.class, ScheduledActivities.class}, version = 12)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract CatDao catDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "timeboxDatabase").fallbackToDestructiveMigration().build();
                }
            }
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
