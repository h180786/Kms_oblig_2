package com.example.kms.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.test.espresso.idling.CountingIdlingResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class QuizViewModel extends AndroidViewModel {

    private final LiveData<List<Quiz>> allQuestions;
    private final LiveData<List<Quiz>> galleryQuestions;
    private final MutableLiveData<List<Quiz>> _allQuestions = new MutableLiveData<>();
    private final MutableLiveData<List<Quiz>> _shuffledQuizzes = new MutableLiveData<>();
    private final LiveData<List<Quiz>> shuffledQuizzes;
    private final MutableLiveData<Integer> _score = new MutableLiveData<>(0);
    private final LiveData<Integer> score = _score;
    private final MutableLiveData<Integer> _totalTries = new MutableLiveData<>(0);
    private final LiveData<Integer> totalTries = _totalTries;
    private  ArrayList<String> answers;
    private boolean shuffledAnswers = false;
    private final SavedStateHandle handle;
    private QuizRepository repository;
    private final CountingIdlingResource mIdlingResource = new CountingIdlingResource("quizIdlingResource");

    public CountingIdlingResource getIdlingResource() {
        return mIdlingResource;
    }


    public QuizViewModel(@NonNull Application application) {
        super(application);
        this.handle = new SavedStateHandle();
        this.repository = new QuizRepository(application);
        this.allQuestions = repository.getAllQuestions();
        this.galleryQuestions = Transformations.switchMap(allQuestions, quizzes -> {
            if (quizzes != null && !quizzes.isEmpty()) {
                List<Quiz> shuffledList = new ArrayList<>(quizzes);
                _allQuestions.setValue(shuffledList);
            }
            return _allQuestions;
        });
        this.shuffledQuizzes = Transformations.switchMap(allQuestions, quizzes -> {
            if (quizzes != null && !quizzes.isEmpty()) {
                List<Quiz> shuffledList = new ArrayList<>(quizzes);
                Collections.shuffle(shuffledList);
                _shuffledQuizzes.setValue(shuffledList);
            }
            return _shuffledQuizzes;
        });
    }

    // Existing constructor
    public QuizViewModel(Application application, SavedStateHandle savedStateHandle, LiveData<List<Quiz>> allQuizzes, QuizRepository repository) {
        super(application);
        this.handle = savedStateHandle;
        this.repository = repository;
        this.allQuestions = repository.getAllQuestions();
        this.galleryQuestions = Transformations.switchMap(allQuizzes, quizzes -> {
            if (quizzes != null && !quizzes.isEmpty()) {
                List<Quiz> shuffledList = new ArrayList<>(quizzes);
                _allQuestions.setValue(shuffledList);
            }
            return _allQuestions;
        });
        this.shuffledQuizzes = Transformations.switchMap(allQuizzes, quizzes -> {
            if (quizzes != null && !quizzes.isEmpty()) {
                List<Quiz> shuffledList = new ArrayList<>(quizzes);
                Collections.shuffle(shuffledList);
                _shuffledQuizzes.setValue(shuffledList);
            }
            return _shuffledQuizzes;
        });
    }

    public LiveData<List<Quiz>> getGalleryQuestions() {
        return galleryQuestions;
    }

    public void deleteQuiz(Quiz quiz) {
        repository.delete(quiz);
    }
    public void insertQuiz(Quiz quiz) {
        repository.insert(quiz);
    }

    public void sortAtoZ() {
        List<Quiz> newQuiz = allQuestions.getValue();
        assert newQuiz != null;
        newQuiz.sort(Comparator.comparing(Quiz::getAnswer));
        _allQuestions.setValue(newQuiz);
    }

    public void sortZtoA() {
        List<Quiz> newQuiz = allQuestions.getValue();
        assert newQuiz != null;
        newQuiz.sort(Comparator.comparing(Quiz::getAnswer).reversed());
        _allQuestions.setValue(newQuiz);
    }

    public LiveData<List<Quiz>> getShuffledQuizzes() {
        return shuffledQuizzes;
    }

    public LiveData<Integer> getScore() {
        return score;
    }

    public LiveData<Integer> getTotalTries() {
        return totalTries;
    }

    private final List<String> randomNames = Arrays.asList("Kitten", "Puppy", "Calf", "Foal", "Cub", "Chick", "Fawn", "Gosling", "Piglet", "Lamb");

    public ArrayList<String> getAnswers() {
        if (!shuffledAnswers) {
            Quiz currentQuiz = Objects.requireNonNull(shuffledQuizzes.getValue()).get(0);
            String correctAnswer = currentQuiz.getAnswer();
            ArrayList<String> answers = new ArrayList<>(randomNames);
            answers.remove(correctAnswer); // Remove correct answer if it exists in random names
            Collections.shuffle(answers);
            answers = new ArrayList<>(answers.subList(0, 2)); // Pick 2 random names
            answers.add(correctAnswer); // Add the correct answer
            Collections.shuffle(answers); // Shuffle to randomize the position of the correct answer
            shuffledAnswers = true;
            this.answers = answers;
        }
        return answers;
    }

    public void goToNextQuiz() {
        List<Quiz> newQuiz = shuffledQuizzes.getValue();
        assert newQuiz != null;
        newQuiz.remove(0);
        _shuffledQuizzes.setValue(newQuiz);
        shuffledAnswers = false;
    }

    public void incrementScore() {
        if (_score.getValue() != null) {
            _score.setValue(_score.getValue() + 1);
        }
    }

    public void incrementTotalTries() {
        if (_totalTries.getValue() != null) {
            _totalTries.setValue(_totalTries.getValue() + 1);
        }
    }


    public void saveButtonColor(String buttonKey, String color) {
        handle.set(buttonKey, color);
    }
    public String getButtonColor(String buttonKey) {
        return handle.get(buttonKey);
    }
    public void removeButtonColor(List<String> buttonKey) {
        buttonKey.forEach(handle::remove);
    }


}
