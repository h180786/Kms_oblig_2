package com.example.kms.ViewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizRepository {
    private final QuizDao quizDao;
    private final LiveData<List<Quiz>> allQuestions;

    QuizRepository(Application application) {
        QuizRoomDatabase db = QuizRoomDatabase.getDatabase(application);
        quizDao = db.quizDao();
        allQuestions = quizDao.getAllQuestions();
    }

    LiveData<List<Quiz>> getAllQuestions() {
        return allQuestions;
    }

    void insert(Quiz quiz) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> quizDao.insertQuestion(quiz));
        executor.shutdown();
    }

    void delete(Quiz quiz) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> quizDao.deleteQuiz(quiz));
        executor.shutdown();
    }

    void deleteAll() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(quizDao::deleteAll);
        executor.shutdown();
    }

}
