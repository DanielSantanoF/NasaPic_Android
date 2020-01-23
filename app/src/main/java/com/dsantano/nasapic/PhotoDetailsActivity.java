package com.dsantano.nasapic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dsantano.nasapic.transformations.DateTransformer;
import com.dsantano.nasapic.transformations.UrlToUrlThumbnail;

import java.util.Objects;

public class PhotoDetailsActivity extends AppCompatActivity {

    String photoUrl, tittle, description, date, urlToLoad;
    ImageView ivPhoto;
    int errorToLoad;
    TextView txtTittle, txtDate, txtDescription;
    DateTransformer dateTransformer = new DateTransformer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        photoUrl = Objects.requireNonNull(getIntent().getExtras()).getString("nasaPhotoSelected");
        tittle = Objects.requireNonNull(getIntent().getExtras()).getString("nasaTittleSelected");
        description = Objects.requireNonNull(getIntent().getExtras()).getString("nasaDescriptionSelected");
        date = Objects.requireNonNull(getIntent().getExtras()).getString("nasaDateSelected");

        ivPhoto = findViewById(R.id.imageViewPhotoNasaPic);
        txtTittle = findViewById(R.id.textViewPhotoTittleNasaPic);
        txtDate = findViewById(R.id.textViewPhotoNasaPic);
        txtDescription = findViewById(R.id.textViewPhotoDescriptionNasaPic);
        txtDescription.setMovementMethod(new ScrollingMovementMethod());

        txtTittle.setText(tittle);
        txtDescription.setText(description);
        txtDate.setText(dateTransformer.dateTransformation(date));

        if(photoUrl.contains("www.youtube")) {
            UrlToUrlThumbnail transformer = new UrlToUrlThumbnail(photoUrl);
            urlToLoad = transformer.urlToThumbnail();
            errorToLoad = R.drawable.ic_youtube_logo;
        } else {
            urlToLoad = photoUrl;
            errorToLoad = R.drawable.ic_no_image_loaded;
        }
        Glide
                .with(PhotoDetailsActivity.this)
                .load(urlToLoad)
                .error(Glide.with(PhotoDetailsActivity.this).load(errorToLoad))
                .thumbnail(Glide.with(PhotoDetailsActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
                .into(ivPhoto);

        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoUrl.contains("https://www.youtube.com")){
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(photoUrl));
                    PhotoDetailsActivity.this.startActivity(webIntent);
                } else {
                    Intent i = new Intent(
                            PhotoDetailsActivity.this,
                            DetailPhotoActivity.class
                    );
                    i.putExtra("photoUrl", photoUrl);
                    startActivity(i);
                }
            }
        });
    }
}
