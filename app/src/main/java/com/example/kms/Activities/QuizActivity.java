package com.example.kms.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.kms.Fragments.ButtonFragment;
import com.example.kms.Fragments.ImageFragment;
import com.example.kms.Fragments.ResultFragment;
import com.example.kms.Fragments.ScoreFragment;
import com.example.kms.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {

    private ActivityQuizBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ImageFragment pictureFragment = new ImageFragment();
            ButtonFragment buttonFragment = new ButtonFragment();
            ScoreFragment scoreFragment = new ScoreFragment();

            ft.add(binding.pictureFragment.getId(), pictureFragment);
            ft.add(binding.buttonFragment.getId(), buttonFragment);
            ft.add(binding.scoreFragment.getId(), scoreFragment);
            ft.commit();
        }
    }

    public void getResultScreen() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ScoreFragment scoreFragment = (ScoreFragment) fm.findFragmentById(binding.scoreFragment.getId());
        ButtonFragment buttonFragment = (ButtonFragment) fm.findFragmentById(binding.buttonFragment.getId());
        ImageFragment pictureFragment = (ImageFragment) fm.findFragmentById(binding.pictureFragment.getId());
        ResultFragment resultFragment = new ResultFragment();

        if (pictureFragment != null && buttonFragment != null && scoreFragment != null) {
            ft.remove(pictureFragment);
            ft.remove(buttonFragment);
            ft.remove(scoreFragment);
            ft.add(binding.resultFragment.getId(), resultFragment);
            ft.commit();
        }
    }
}
