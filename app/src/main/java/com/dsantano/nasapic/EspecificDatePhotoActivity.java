package com.dsantano.nasapic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especific_date_photo);

        photoDate = Objects.requireNonNull(getIntent().getExtras()).getString("selectedDate");

        new DownloadEspecificPhotoFromApi().execute();

        ivphoto = findViewById(R.id.imageViewPhotoDetail);
        ivphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        EspecificDatePhotoActivity.this,
                        DetailPhotoActivity.class
                );
                i.putExtra("photoUrl", photoUrl);
                startActivity(i);
            }
        });

    }

    private class DownloadEspecificPhotoFromApi extends AsyncTask<Void, Void, NasaPicture> {

        NasaPicture result;

        @Override
        protected NasaPicture doInBackground(Void... voids) {
            result = api.getPicOfAnyDate(photoDate);
            return result;
        }

        @Override
        protected void onPostExecute(NasaPicture nasaPicture) {
            txtTittle = findViewById(R.id.textViewPhotoDetailTittle);
            txtDescription = findViewById(R.id.textViewPhotoDetailDescription);
            txtdate = findViewById(R.id.textViewPhotoDetailDate);
            ivphoto = findViewById(R.id.imageViewPhotoDetail);

            photoUrl = nasaPicture.getUrl();
            txtTittle.setText(nasaPicture.getTitle());
            txtdate.setText(nasaPicture.getDate());
            txtDescription.setText(nasaPicture.getExplanation());
            txtDescription.setMovementMethod(new ScrollingMovementMethod());
            Glide
                    .with(EspecificDatePhotoActivity.this)
                    .load(nasaPicture.getUrl())
                    .error(R.drawable.ic_no_image_loaded)
                    .thumbnail(Glide.with(EspecificDatePhotoActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
                    .into(ivphoto);
        }
    }
}
