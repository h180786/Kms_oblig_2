package com.example.kms.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    private final String rightColor = "#439936";


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
                Quiz currentQuiz = quizzes.get(0);
                correctAnswer = currentQuiz.getAnswer();

                ArrayList<String> answers = viewModel.getAnswers();

                binding.button1.setText(answers.get(0));
                if(viewModel.getButtonColor(String.valueOf(binding.button1.getId())) != null){
                    binding.button1.setBackgroundColor(android.graphics.Color.parseColor(viewModel.getButtonColor(String.valueOf(binding.button1.getId()))));
                    binding.button1.setClickable(false);
                } else {
                    binding.button1.setBackgroundColor(android.graphics.Color.parseColor(defaultColor));
                    binding.button1.setClickable(true);
                }

                binding.button2.setText(answers.get(1));
                if(viewModel.getButtonColor(String.valueOf(binding.button2.getId())) != null){
                    binding.button2.setBackgroundColor(android.graphics.Color.parseColor(viewModel.getButtonColor(String.valueOf(binding.button2.getId()))));
                    binding.button2.setClickable(false);
                } else {
                    binding.button2.setBackgroundColor(android.graphics.Color.parseColor(defaultColor));
                    binding.button2.setClickable(true);
                }

                binding.button3.setText(answers.get(2));
                if(viewModel.getButtonColor(String.valueOf(binding.button3.getId())) != null) {
                    binding.button3.setBackgroundColor(android.graphics.Color.parseColor(viewModel.getButtonColor(String.valueOf(binding.button3.getId()))));
                    binding.button3.setClickable(false);
                } else {
                    binding.button3.setBackgroundColor(android.graphics.Color.parseColor(defaultColor));
                    binding.button3.setClickable(true);
                }
            }
            List<Button> buttons = Arrays.asList(binding.button1, binding.button2, binding.button3);
            int i = 1;
            for(Button button: buttons){
                if(button.getText().equals(correctAnswer)){
                    button.setTag("Right answer");
                } else {
                    button.setTag("Wrong answer " + i);
                    i++;
                }
            }
        });

        View.OnClickListener answerButtonClickListener = v -> {

            String chosenAnswer = ((Button) v).getText().toString();
            if (chosenAnswer.equals(correctAnswer)) {

                v.setBackgroundColor(android.graphics.Color.parseColor(rightColor));
                viewModel.saveButtonColor(String.valueOf(v.getId()),rightColor);

                viewModel.incrementScore();
                viewModel.incrementTotalTries();

                if(viewModel.getShuffledQuizzes().getValue().size() == 1){
//                    ((QuizActivity) getActivity()).getResultScreen();
                }

                if(viewModel.getShuffledQuizzes().getValue().size() > 1) {
                    List<String> buttons =
                            Arrays.asList(
                                    String.valueOf(binding.button1.getId()),
                                    String.valueOf(binding.button2.getId()),
                                    String.valueOf(binding.button3.getId())
                            );

                }

            } else {
                v.setBackgroundColor(android.graphics.Color.parseColor(wrongColor));
                viewModel.saveButtonColor(String.valueOf(v.getId()),wrongColor);
                viewModel.incrementTotalTries();
            }
            v.setClickable(false);
        };
        binding.button1.setOnClickListener(answerButtonClickListener);
        binding.button2.setOnClickListener(answerButtonClickListener);
        binding.button3.setOnClickListener(answerButtonClickListener);

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}