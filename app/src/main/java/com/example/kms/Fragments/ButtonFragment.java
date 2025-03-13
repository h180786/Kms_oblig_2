// ButtonFragment.java
package com.example.kms.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kms.Activities.QuizActivity;
import com.example.kms.ViewModel.Quiz;
import com.example.kms.ViewModel.QuizViewModel;
import com.example.kms.databinding.FragmentButtonsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonFragment extends Fragment {

    private FragmentButtonsBinding binding;
    private QuizViewModel viewModel;
    private String correctAnswer;

    private final String defaultColor = "#6f3b96";
    private final String wrongColor = "#d13434";


    public ButtonFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentButtonsBinding.inflate(inflater, container, false);

        viewModel.getShuffledQuizzes().observe(getViewLifecycleOwner(), quizzes -> {
            if (quizzes != null && !quizzes.isEmpty()) {
                updateButtons(quizzes.get(0));
            }
        });

        View.OnClickListener answerButtonClickListener = v -> {
            String chosenAnswer = ((Button) v).getText().toString();
            if (chosenAnswer.equals(correctAnswer)) {

                viewModel.incrementScore();
                viewModel.incrementTotalTries();

                Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                if (viewModel.getShuffledQuizzes().getValue().size() == 1) {
                    ((QuizActivity) getActivity()).getResultScreen();
                } else {
                    viewModel.goToNextQuiz();
                    updateButtons(viewModel.getShuffledQuizzes().getValue().get(0)); // Update buttons for the next quiz
                }

            } else {
                Toast.makeText(getContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                v.setBackgroundColor(android.graphics.Color.parseColor(wrongColor));
                viewModel.saveButtonColor(String.valueOf(v.getId()), wrongColor);
                viewModel.incrementTotalTries();
            }
            v.setClickable(false);
        };

        binding.button1.setOnClickListener(answerButtonClickListener);
        binding.button2.setOnClickListener(answerButtonClickListener);
        binding.button3.setOnClickListener(answerButtonClickListener);

        return binding.getRoot();
    }

    private void updateButtons(Quiz currentQuiz) {
        correctAnswer = currentQuiz.getAnswer();

        ArrayList<String> answers = viewModel.getAnswers();

        if (answers.size() >= 3) {
            binding.button1.setText(answers.get(0));
            setButtonState(binding.button1);

            binding.button2.setText(answers.get(1));
            setButtonState(binding.button2);

            binding.button3.setText(answers.get(2));
            setButtonState(binding.button3);
        }

        List<Button> buttons = Arrays.asList(binding.button1, binding.button2, binding.button3);
        int i = 1;
        for (Button button : buttons) {
            if (button.getText().equals(correctAnswer)) {
                button.setTag("Right answer");
            } else {
                button.setTag("Wrong answer " + i);
                i++;
            }
            button.setClickable(true); // Make the button clickable
            button.setBackgroundColor(android.graphics.Color.parseColor(defaultColor)); // Reset button color
        }
    }

    private void setButtonState(Button button) {
        if (viewModel.getButtonColor(String.valueOf(button.getId())) != null) {
            button.setBackgroundColor(android.graphics.Color.parseColor(viewModel.getButtonColor(String.valueOf(button.getId()))));
            button.setClickable(false);
        } else {
            button.setBackgroundColor(android.graphics.Color.parseColor(defaultColor));
            button.setClickable(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}