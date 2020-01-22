package com.dsantano.nasapic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dsantano.nasapic.api.NasaPicture;

import java.util.Objects;

public class HistoricActivity extends AppCompatActivity implements INasaPictureListener{

    String today, monthAgoToday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);
    }

    @Override
    public void onNasaPictureItemClick(NasaPicture n) {

    }
}
