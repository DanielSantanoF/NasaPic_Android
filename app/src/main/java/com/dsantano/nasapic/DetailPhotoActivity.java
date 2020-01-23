package com.dsantano.nasapic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class DetailPhotoActivity extends AppCompatActivity {

    String photo;
    ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_photo);

        ivPhoto = findViewById(R.id.imageViewPhotoHistoric);

        photo = Objects.requireNonNull(getIntent().getExtras()).getString("photoUrl");

        Glide
                    .with(this)
                    .load(photo)
                    .error(Glide.with(DetailPhotoActivity.this).load(R.drawable.ic_no_image_loaded))
                    .thumbnail(Glide.with(this).load(R.drawable.loading_killer_whale_gif).centerCrop())
                    .into(ivPhoto);
    }
}
