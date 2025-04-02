package com.example.kms.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kms.RecyclerView.RecyclerViewAdapter;
import com.example.kms.RecyclerView.RecyclerViewInterface;
import com.example.kms.ViewModel.Quiz;
import com.example.kms.databinding.ActivityGalleryBinding;
import com.example.kms.ViewModel.QuizViewModel;

import java.io.File;

public class GalleryActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ActivityResultLauncher<Intent> launcher;
    private QuizViewModel viewModel;
    private ActivityGalleryBinding binding;
    private RecyclerViewAdapter adapter;
    private Uri cameraUri;
    private static boolean isTesting = false;
    public static void setTesting(boolean isTesting) {
        GalleryActivity.isTesting = isTesting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        ViewCompat.setOnApplyWindowInsetsListener(binding.gallery, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(binding.recyclerView.getId());

        viewModel.getGalleryQuestions().observe(this, quizzes -> {
            adapter = new RecyclerViewAdapter(this, quizzes, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                assert data != null;
                Uri imageUri = data.getData();

                if (imageUri != null) {
                  if(!isTesting){
                      getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                  }

                    Intent intent = new Intent(this, NewImageActivity.class);
                    intent.putExtra("imageUri", imageUri.toString());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, NewImageActivity.class);
                    intent.putExtra("imageUri", cameraUri.toString());
                    startActivity(intent);
                }
            }
        });

        binding.sortAZButton.setOnClickListener(v -> viewModel.sortAtoZ());
        binding.sortZAButton.setOnClickListener(v -> viewModel.sortZtoA());
        binding.galleryButton.setOnClickListener(v -> openPhoneGallery());
        binding.cameraButton.setOnClickListener(v -> openCamera());
        }

    @Override
    public void deleteQuiz(int pos) {

        Quiz quiz = viewModel.getGalleryQuestions().getValue().get(pos);
        viewModel.deleteQuiz(quiz);
    }

    private void openPhoneGallery(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        launcher.launch(intent);
    }

    private void openCamera() {

        File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), SystemClock.currentThreadTimeMillis()+ "temp_image.jpg");
        cameraUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", imageFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        launcher.launch(intent);

    }

}
