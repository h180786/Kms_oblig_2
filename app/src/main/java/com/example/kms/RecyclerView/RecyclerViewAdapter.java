package com.example.kms.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kms.R;
import com.example.kms.ViewModel.Quiz;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<Quiz> quizModels;
    private final RecyclerViewInterface recyclerViewInterface;
    public RecyclerViewAdapter(Context context,List<Quiz> quizModels, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.quizModels = quizModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageURI(Uri.parse(quizModels.get(position).getPicture()));
        holder.name.setText(quizModels.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return quizModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton deleteButton;
        TextView name;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.picture);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            name = itemView.findViewById(R.id.Name);

            deleteButton.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.deleteQuiz(pos);
                    }


                }
            });
        }}
}
