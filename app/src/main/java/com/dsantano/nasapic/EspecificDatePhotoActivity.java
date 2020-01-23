package com.dsantano.nasapic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dsantano.nasapic.api.NasaApi;
import com.dsantano.nasapic.api.NasaPicture;

import java.util.Objects;

public class EspecificDatePhotoActivity extends AppCompatActivity {

    String apiKey = "vOn6qgqSS0M84sxymRERfRPyAjMc9M9DF6kZ43AS";
    TextView txtTittle, txtDescription, txtdate;
    ImageView ivphoto;
    NasaApi api = new NasaApi(apiKey);
    String photoDate, photoUrl;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especific_date_photo);

        progressBar = findViewById(R.id.progressBarLoadingFullNasaPic);

        photoDate = Objects.requireNonNull(getIntent().getExtras()).getString("selectedDate");

        new DownloadEspecificPhotoFromApi().execute();

        ivphoto = findViewById(R.id.imageViewPhotoNasaPic);
        ivphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoUrl.contains("https://www.youtube.com")){
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(photoUrl));
                    EspecificDatePhotoActivity.this.startActivity(webIntent);
                } else {
                    Intent i = new Intent(
                            EspecificDatePhotoActivity.this,
                            DetailPhotoActivity.class
                    );
                    i.putExtra("photoUrl", photoUrl);
                    startActivity(i);
                }
            }
        });

    }

    private class DownloadEspecificPhotoFromApi extends AsyncTask<Void, Void, NasaPicture> {

        NasaPicture result;

        @Override
        protected void onPreExecute() {
            txtTittle = findViewById(R.id.textViewPhotoTittleNasaPic);
            txtDescription = findViewById(R.id.textViewPhotoDescriptionNasaPic);
            txtdate = findViewById(R.id.textViewPhotoNasaPic);
            ivphoto = findViewById(R.id.imageViewPhotoNasaPic);
            txtTittle.setVisibility(View.GONE);
            txtDescription.setVisibility(View.GONE);
            txtdate.setVisibility(View.GONE);
            ivphoto.setVisibility(View.GONE);
        }

        @Override
        protected NasaPicture doInBackground(Void... voids) {
            result = api.getPicOfAnyDate(photoDate);
            return result;
        }

        @Override
        protected void onPostExecute(NasaPicture nasaPicture) {
            progressBar.setVisibility(View.GONE);
            txtTittle.setVisibility(View.VISIBLE);
            txtDescription.setVisibility(View.VISIBLE);
            txtdate.setVisibility(View.VISIBLE);
            ivphoto.setVisibility(View.VISIBLE);

            txtTittle = findViewById(R.id.textViewPhotoTittleNasaPic);
            txtDescription = findViewById(R.id.textViewPhotoDescriptionNasaPic);
            txtdate = findViewById(R.id.textViewPhotoNasaPic);
            ivphoto = findViewById(R.id.imageViewPhotoNasaPic);

            photoUrl = nasaPicture.getUrl();
            txtTittle.setText(nasaPicture.getTitle());
            txtdate.setText(nasaPicture.getDate());
            txtDescription.setText(nasaPicture.getExplanation());
            txtDescription.setMovementMethod(new ScrollingMovementMethod());
            if(photoUrl.contains("www.youtube")) {
                String urlThumbnailYoutube = photoUrl.split("/")[4];
                String thumbnailId = urlThumbnailYoutube.split("\\?")[0];
                String youtubeUrl = "https://img.youtube.com/vi/" + thumbnailId +"/hqdefault.jpg";
                Glide
                        .with(EspecificDatePhotoActivity.this)
                        .load(youtubeUrl)
                        .error(Glide.with(EspecificDatePhotoActivity.this).load(R.drawable.ic_youtube_logo))
                        .thumbnail(Glide.with(EspecificDatePhotoActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
                        .into(ivphoto);
            } else {
                Glide
                        .with(EspecificDatePhotoActivity.this)
                        .load(nasaPicture.getUrl())
                        .error(Glide.with(EspecificDatePhotoActivity.this).load(R.drawable.ic_no_image_loaded))
                        .thumbnail(Glide.with(EspecificDatePhotoActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
                        .into(ivphoto);
            }
        }
    }
}
