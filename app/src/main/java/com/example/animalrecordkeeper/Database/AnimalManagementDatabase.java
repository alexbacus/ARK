package com.example.animalrecordkeeper.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.animalrecordkeeper.DAO.AnimalDAO;
import com.example.animalrecordkeeper.DAO.FeedingDAO;
import com.example.animalrecordkeeper.DAO.GroupDAO;
import com.example.animalrecordkeeper.Entities.AnimalEntity;
import com.example.animalrecordkeeper.Entities.FeedingEntity;
import com.example.animalrecordkeeper.Entities.GroupEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {GroupEntity.class, AnimalEntity.class, FeedingEntity.class}, version = 1, exportSchema = false)
public abstract class AnimalManagementDatabase extends RoomDatabase {
    public abstract GroupDAO groupDAO();
    public abstract AnimalDAO animalDAO();
    public abstract FeedingDAO feedingDAO();

    private static volatile AnimalManagementDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AnimalManagementDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnimalManagementDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AnimalManagementDatabase.class, "animal_management_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
