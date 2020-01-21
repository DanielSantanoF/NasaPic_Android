package com.dsantano.nasapic;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsantano.nasapic.api.NasaApi;
import com.dsantano.nasapic.api.NasaPicture;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link INasaPictureListener}
 * interface.
 */
public class NasaPictureFragmentList extends Fragment {

    String apiKey = "vOn6qgqSS0M84sxymRERfRPyAjMc9M9DF6kZ43AS";
    NasaApi api = new NasaApi(apiKey);

    RecyclerView recyclerView;
    Context context;
    String today, monthAgoDay;

    private int mColumnCount = 2;

    private INasaPictureListener mListener;

    private List<NasaPicture> listNasaPictures = new ArrayList<NasaPicture>();

    public NasaPictureFragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nasapicture_list, container, false);

        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            new DownloadPhotosFromApi().execute();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof INasaPictureListener) {
            mListener = (INasaPictureListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement INasaPictureListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class DownloadPhotosFromApi extends AsyncTask<Void, Void, List<NasaPicture>>{

        List<NasaPicture> result;

        @Override
        protected void onPreExecute() {
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
            today = year + "-" + monthString + "-" + dayString;
            monthAgoDay = year + "-" + monthAgoString + "-" + dayString;
        }

        @Override
        protected List<NasaPicture> doInBackground(Void... voids) {
            //result = api.getPicOfDateInterval("01-12-2019","01-01-2020");
            //result = api.getPicOfDateInterval(monthAgoDay,today);
            //return result;
            return null;
        }

        @Override
        protected void onPostExecute(List<NasaPicture> list) {
            //listNasaPictures = list;
            listNasaPictures.add(new NasaPicture("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg","Quadrantid Meteors through Orion","Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.","2020 January 20"));
            listNasaPictures.add(new NasaPicture("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg","Quadrantid Meteors through Orion","Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.","2020 January 20"));
            listNasaPictures.add(new NasaPicture("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg","Quadrantid Meteors through Orion","Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.","2020 January 20"));
            listNasaPictures.add(new NasaPicture("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg","Quadrantid Meteors through Orion","Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.","2020 January 20"));
            listNasaPictures.add(new NasaPicture("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg","Quadrantid Meteors through Orion","Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.","2020 January 20"));
            listNasaPictures.add(new NasaPicture("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg","Quadrantid Meteors through Orion","Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.","2020 January 20"));
            listNasaPictures.add(new NasaPicture("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg","Quadrantid Meteors through Orion","Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.","2020 January 20"));
            listNasaPictures.add(new NasaPicture("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg","Quadrantid Meteors through Orion","Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.","2020 January 20"));
            listNasaPictures.add(new NasaPicture("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg","Quadrantid Meteors through Orion","Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.","2020 January 20"));
            listNasaPictures.add(new NasaPicture("https://apod.nasa.gov/apod/image/2001/QuadrantidsOrion_Horalek_960_annotated.jpg","Quadrantid Meteors through Orion","Why are these meteor trails nearly parallel? Because they were all shed by the same space rock and so can be traced back to the same direction on the sky: the radiant of the Quadrantid Meteor Shower. This direction used to be toward the old constellation of Quadrans Muralis, hence the name Quadrantids, but when the International Astronomical Union formulated its list of modern constellations in 1922, this constellation did not make the list. Even though the meteors are now considered to originate from the recognized constellation of Bootes, the old name stuck. Regardless of the designation, every January the Earth moves through a dust stream and bits of this dust glow as meteors as they heat up in Earth's atmosphere. The featured image composite was taken on January 4 with a picturesque snowy Slovakian landscape in the foreground, and a deep-exposure sky prominently featuring the constellation Orion in the background. The red star Betelgeuse appears unusually dim -- its fading over the past few months is being tracked by astronomers.","2020 January 20"));
            MyNasaPictureRecyclerViewAdapter adapter = new MyNasaPictureRecyclerViewAdapter(
                    context,
                    R.layout.fragment_nasapicture,
                    listNasaPictures);
            recyclerView.setAdapter(adapter);
        }
    }

}
