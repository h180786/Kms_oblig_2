package com.example.kms.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuizDao {
    @Query("SELECT * FROM quiz_table")
    LiveData<List<Quiz>> getAllQuestions();
    @Insert
    void insertQuestion(Quiz quiz);
    @Delete
    void deleteQuiz(Quiz quiz);
    @Query("DELETE FROM quiz_table")
    void deleteAll();
    @Query("SELECT COUNT(*) FROM quiz_table")
    int getQuizCount();
}
