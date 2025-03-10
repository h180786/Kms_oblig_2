package com.example.kms.ViewModel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quiz_table")
public class Quiz {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "picture")
    private String picture;

    @ColumnInfo(name = "answer")
    private String answer;

    public Quiz(@NonNull String picture, String answer){
        this.picture = picture;
        this.answer = answer;
    }

    @NonNull
    public String getPicture() {
        return picture;
    }
    public String getAnswer() {
        return answer;
    }
}
