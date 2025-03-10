package com.example.kms.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kms.R;
import com.example.kms.ViewModel.QuizViewModel;
import com.example.kms.databinding.FragmentQuizBinding;

public class ImageFragment extends Fragment {
    private QuizViewModel viewModel;
    private FragmentQuizBinding binding;

    public ImageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);

        viewModel.getShuffledQuizzes().observe(getViewLifecycleOwner(), quizzes -> {

            binding.imageView2.setImageURI(Uri.parse(quizzes.get(0).getPicture()));
            Log.d("PictureFragment", "Picture URI: " + quizzes.get(0).getPicture());
            Log.d("PictureFragment", "Picture BANANA: " + R.drawable.duck);
            Log.d("PictureFragment", "Picture ORANGE: " + R.drawable.kitten);
            Log.d("PictureFragment", "Picture URI: " + R.drawable.puppy);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
