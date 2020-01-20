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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String apiKey = "vOn6qgqSS0M84sxymRERfRPyAjMc9M9DF6kZ43AS";
    TextView txtTittle, txtDescription, txtdate;
    ImageView ivphoto;
    ImageButton btnDate;
    Button btnHistoric;
    NasaApi api = new NasaApi(apiKey);
    String photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadDailyPhotoFromApi().execute();

        btnDate = findViewById(R.id.imageButtonSelectDate);
        btnHistoric = findViewById(R.id.buttonHistoric);
        ivphoto = findViewById(R.id.imageViewFromApi);

        btnDate.setOnClickListener(this);
        btnHistoric.setOnClickListener(this);

        //https://programacionymas.com/blog/como-pedir-fecha-android-usando-date-picker
        ivphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        MainActivity.this,
                        DetailPhotoActivity.class
                );
                i.putExtra("photoUrl", photoUrl);
                startActivity(i);
            }
        });

//        txtTittle.setText("Quadrantid Meteors through Orion");
//        txtdate.setText("2020 January 20");
//        txtDescription.setText("Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.");
//        txtDescription.setMovementMethod(new ScrollingMovementMethod());
//        Glide
//                .with(this)
//                .load("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg")
//                .into(ivphoto);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.imageButtonSelectDate){
            Intent i = new Intent(
                    MainActivity.this,
                    EspecificDatePhotoActivity.class
            );
            i.putExtra("selectedDate", "2020-01-01");
            startActivity(i);
        } if(id == R.id.buttonHistoric){
            LocalDateTime todayDate = LocalDateTime.now();
            int day = todayDate.getDayOfMonth();
            String dayString = "";
            int year = todayDate.getYear();
            int month = todayDate.getMonthValue();
            String monthString = "";
            int monthAgo = month -1;
            String monthAgoString = "";
            if(day == 1 || day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7 || day == 8 || day == 9){
                dayString = "0" + day;
            } else if(month == 1 || month == 2 || month == 3 || month == 4 || month == 5 || month == 6 || month == 7 || month == 8 || month == 9){
                monthString = "0" + month;
                if(monthAgo == 0){
                    monthAgo = 12;
                    monthAgoString = String.valueOf(monthAgo);
                } else {
                    monthAgoString = "0" + month;
                }
            }
            String today = year + "-" + monthString + "-" + dayString;
            String monthAgoDay = year + "-" + monthAgoString + "-" + dayString;
            Intent i = new Intent(
                    MainActivity.this,
                    HistoricActivity.class
            );
            i.putExtra("dateOfToday", today);
            i.putExtra("dateOfMonthAgo", monthAgoDay);
            startActivity(i);
        }
    }

    private class DownloadDailyPhotoFromApi extends AsyncTask<Void, Void, NasaPicture> {

        NasaPicture result;

        @Override
        protected NasaPicture doInBackground(Void... voids) {
            //result = api.getPicOfToday();
            //return result;
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
//                    .with(MainActivity.this)
//                    .load(photoUrl)
//                    .thumbnail(Glide.with(MainActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
//                    .into(ivphoto);
            photoUrl = "https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg";
            txtTittle.setText("Quadrantid Meteors through Orion");
            txtdate.setText("2020 January 20");
            txtDescription.setText("Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.");
            txtDescription.setMovementMethod(new ScrollingMovementMethod());
            Glide
                    .with(MainActivity.this)
                    .load("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg")
                    .thumbnail(Glide.with(MainActivity.this).load(R.drawable.loading_killer_whale_gif).centerCrop())
                    .into(ivphoto);
        }
    }

}
