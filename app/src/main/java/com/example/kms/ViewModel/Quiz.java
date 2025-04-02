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

    @ColumnInfo(name = "lat")
    private float lat;

    @ColumnInfo(name = "long")
    private float lng;

    public Quiz(@NonNull String picture, String answer, float lat, float lng) {
        this.picture = picture;
        this.answer = answer;
        this.lat = lat;
        this.lng = lng;

    }

    @NonNull
    public String getPicture() {
        return picture;
    }
    public String getAnswer() {
        return answer;
    }

    public float getLat() {
        return lat;
    }
    public float getLng() {
        return lng;
    }
}
