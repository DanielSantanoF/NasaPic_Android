package com.dsantano.nasapic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dsantano.nasapic.api.NasaApi;
import com.dsantano.nasapic.api.NasaPicture;
import com.dsantano.nasapic.datePicker.DialogDatePickerFragment;
import com.dsantano.nasapic.datePicker.IDatePickerListener;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IDatePickerListener {

    String apiKey = "vOn6qgqSS0M84sxymRERfRPyAjMc9M9DF6kZ43AS";
    TextView txtTittle, txtDescription, txtdate;
    ImageView ivphoto;
    ImageButton btnDate;
    Button btnHistoric;
    NasaApi api = new NasaApi(apiKey);
    String photoUrl, dateSelected;
    DialogFragment datePickerFragment = DialogDatePickerFragment.newInstance(this);
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadDailyPhotoFromApi().execute();

        btnDate = findViewById(R.id.imageButtonSelectDate);
        btnHistoric = findViewById(R.id.buttonHistoric);
        ivphoto = findViewById(R.id.imageViewPhotoNasaPic);
        progressBar = findViewById(R.id.progressBarLoadingFullNasaPic);

        btnDate.setOnClickListener(this);
        btnHistoric.setOnClickListener(this);

        ivphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoUrl.contains("https://www.youtube.com")){
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(photoUrl));
                    MainActivity.this.startActivity(webIntent);
                } else {
                    Intent i = new Intent(
                            MainActivity.this,
                            DetailPhotoActivity.class
                    );
                    i.putExtra("photoUrl", photoUrl);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.imageButtonSelectDate){
            datePickerFragment.show(getSupportFragmentManager(), "datePicker");
        } if(id == R.id.buttonHistoric){
            Intent i = new Intent(
                    MainActivity.this,
                    HistoricActivity.class
            );
            startActivity(i);
        }
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate dateSelectedOnDialog = new LocalDate(year, month+1, day);
        dateSelected = dateSelectedOnDialog.toString(format);
        if(year > 1995){
            Intent i = new Intent(
                    MainActivity.this,
                    EspecificDatePhotoActivity.class
            );
            i.putExtra("selectedDate", dateSelected);
            datePickerFragment.
                    startActivity(i);
        } else {
            Toast.makeText(this, R.string.valid_date_toast_msg, Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadDailyPhotoFromApi extends AsyncTask<Void, Void, NasaPicture> {

        NasaPicture result;

        @Override
        protected void onPreExecute() {
            txtTittle = findViewById(R.id.textViewPhotoTittleNasaPic);
            txtDescription = findViewById(R.id.textViewPhotoDescriptionNasaPic);
            txtdate = findViewById(R.id.textViewPhotoNasaPic);
            ivphoto = findViewById(R.id.imageViewPhotoNasaPic);
            btnDate = findViewById(R.id.imageButtonSelectDate);
            btnHistoric = findViewById(R.id.buttonHistoric);
            txtTittle.setVisibility(View.GONE);
            txtDescription.setVisibility(View.GONE);
            txtdate.setVisibility(View.GONE);
            ivphoto.setVisibility(View.GONE);
            btnDate.setVisibility(View.GONE);
            btnHistoric.setVisibility(View.GONE);
        }

        @Override
        protected NasaPicture doInBackground(Void... voids) {
            result = api.getPicOfToday();
            return result;
        }

        @Override
        protected void onPostExecute(NasaPicture nasaPicture) {

            progressBar.setVisibility(View.GONE);
            btnDate.setVisibility(View.VISIBLE);
            btnHistoric.setVisibility(View.VISIBLE);
            txtTittle.setVisibility(View.VISIBLE);
            txtDescription.setVisibility(View.VISIBLE);
            txtdate.setVisibility(View.VISIBLE);
            ivphoto.setVisibility(View.VISIBLE);

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
                        .with(MainActivity.this)
                        .load(youtubeUrl)
                        .error(Glide.with(MainActivity.this).load(R.drawable.ic_youtube_logo))
                        .thumbnail(Glide.with(MainActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
                        .into(ivphoto);
            } else {
                Glide
                        .with(MainActivity.this)
                        .load(photoUrl)
                        .error(Glide.with(MainActivity.this).load(R.drawable.ic_no_image_loaded))
                        .thumbnail(Glide.with(MainActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
                        .into(ivphoto);
            }

//            photoUrl = "https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg";
//            txtTittle.setText("Quadrantid Meteors through Orion");
//            txtdate.setText("2020 January 20");
//            txtDescription.setText("Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.");
//            txtDescription.setMovementMethod(new ScrollingMovementMethod());
//            Glide
//                    .with(MainActivity.this)
//                    .load("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg")
//                    .error(Glide.with(MainActivity.this).load(R.drawable.ic_no_image_loaded))
//                    .thumbnail(Glide.with(MainActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
//                    .into(ivphoto);
        }
    }

}
