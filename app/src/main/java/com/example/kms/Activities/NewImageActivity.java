package com.example.kms.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.kms.ViewModel.Quiz;
import com.example.kms.ViewModel.QuizViewModel;
import com.example.kms.databinding.ActivityNewImageBinding;

public class NewImageActivity extends AppCompatActivity {

    private QuizViewModel viewModel;
    private ActivityNewImageBinding binding;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNewImageBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        View view = binding.getRoot();

        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(binding.newQuiz, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        image = getIntent().getStringExtra("imageUri");
        Uri imageUri = Uri.parse(image);
        binding.quizPicture.setImageURI(imageUri);

        binding.submitButton.setOnClickListener(v -> submitButton());

    }

    private void submitButton() {
        EditText rightAns = findViewById(binding.correctAnswer.getId());

        String rightAnswer = rightAns.getText().toString();


        if (rightAnswer.isEmpty()) {
            rightAns.setError("This field is required");
        }

        if (!(rightAnswer.isEmpty()))
        {
            Quiz newQuiz= new Quiz(image, rightAnswer, 0, 0);
            viewModel.insertQuiz(newQuiz);
            finish();
        }
    }
}