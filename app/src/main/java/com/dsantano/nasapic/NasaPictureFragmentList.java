package com.dsantano.nasapic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dsantano.nasapic.api.NasaApi;
import com.dsantano.nasapic.api.NasaPicture;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
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
    String todayDateString, monthAgoDateString, controlDateString;
    LinearLayoutManager manager;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrolOutItems;
    ProgressBar progressBar;
    MyNasaPictureRecyclerViewAdapter adapter;
    LocalDate todayDate, monthAgoDate, controlDate;
    ProgressDialog pDialog;

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
            manager = new GridLayoutManager(context,mColumnCount);
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
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
            todayDate = LocalDate.now();
            todayDateString = todayDate.toString(format);
            monthAgoDate = todayDate.minusMonths(1);
            monthAgoDateString = monthAgoDate.toString(format);
            showProgressDialog();
        }

        @Override
        protected List<NasaPicture> doInBackground(Void... voids) {
            result = api.getPicOfDateInterval(monthAgoDateString,todayDateString);
            return result;
        }

        @Override
        protected void onPostExecute(final List<NasaPicture> list) {
            pDialog.hide();
            Collections.reverse(list);
            listNasaPictures.addAll(list);

            adapter = new MyNasaPictureRecyclerViewAdapter(
                    context,
                    R.layout.fragment_nasapicture,
                    listNasaPictures);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                        isScrolling = true;
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    currentItems = manager.getChildCount();
                    totalItems = manager.getItemCount();
                    scrolOutItems = manager.findFirstVisibleItemPosition();

                    if(isScrolling && (currentItems + scrolOutItems == totalItems)){
                        isScrolling = false;
                        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
                        controlDate = monthAgoDate.minusDays(1);
                        controlDateString = controlDate.toString(format);
                        monthAgoDate = monthAgoDate.minusMonths(1);
                        monthAgoDateString = monthAgoDate.toString(format);
                        new FetchData().execute();
                    }
                }
            });
        }
    }

    private class FetchData extends AsyncTask<Void, Void, List<NasaPicture>>{

        List<NasaPicture> result;

        @Override
        protected void onPreExecute() {
            progressBar = getActivity().findViewById(R.id.progressBarHistoric);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<NasaPicture> doInBackground(Void... voids) {
            result = api.getPicOfDateInterval(monthAgoDateString,controlDateString);
            return result;
        }

        @Override
        protected void onPostExecute(List<NasaPicture> list) {
            Collections.reverse(list);
            listNasaPictures.addAll(list);
            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    public void showProgressDialog(){
        String msg = getString(R.string.progress_dialog_msg);
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(msg);
        pDialog.show();
    }

}
