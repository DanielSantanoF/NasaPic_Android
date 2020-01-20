package com.dsantano.nasapic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especific_date_photo);

        photoDate = Objects.requireNonNull(getIntent().getExtras()).getString("selectedDate");

        new DownloadEspecificPhotoFromApi().execute();

        ivphoto = findViewById(R.id.imageViewFromApi);
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
//            result = api.getPicOfAnyDate(photoDate);
//            return result;
            return null;
        }

        @Override
        protected void onPostExecute(NasaPicture nasaPicture) {
            txtTittle = findViewById(R.id.textViewTittle);
            txtDescription = findViewById(R.id.textViewDescription);
            txtdate = findViewById(R.id.textViewDate);
            ivphoto = findViewById(R.id.imageViewFromApi);

//            photoUrl = nasaPicture.getUrl();
//            txtTittle.setText(nasaPicture.getTitle());
//            txtdate.setText(nasaPicture.getDate());
//            txtDescription.setText(nasaPicture.getExplanation());
//            txtDescription.setMovementMethod(new ScrollingMovementMethod());
//            Glide
//                    .with(EspecificDatePhotoActivity.this)
//                    .load(nasaPicture.getUrl())
//                    .thumbnail(Glide.with(EspecificDatePhotoActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
//                    .into(ivphoto);

            photoUrl = "https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg";
            txtTittle.setText("Quadrantid Meteors through Orion");
            txtdate.setText("2020 January 20");
            txtDescription.setText("Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.");
            txtDescription.setMovementMethod(new ScrollingMovementMethod());
            Glide
                    .with(EspecificDatePhotoActivity.this)
                    .load("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg")
                    .thumbnail(Glide.with(EspecificDatePhotoActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
                    .into(ivphoto);
        }
    }
}
