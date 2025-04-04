package com.example.kms.ViewModel;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Quiz.class}, version = 2)
public abstract class QuizRoomDatabase extends RoomDatabase {

    public abstract QuizDao quizDao();

    private static volatile QuizRoomDatabase INSTANCE;

    static QuizRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuizRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    QuizRoomDatabase.class, "quizMap"
                            )
                            .createFromAsset("database/quizMap.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
