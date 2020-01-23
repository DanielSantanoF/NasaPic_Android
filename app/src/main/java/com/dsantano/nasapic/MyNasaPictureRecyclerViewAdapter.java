package com.dsantano.nasapic;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dsantano.nasapic.api.NasaPicture;
import com.dsantano.nasapic.urlToUrlThumbnail.UrlToUrlThumbnail;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NasaPicture} and makes a call to the
 * specified {@link INasaPictureListener}.
 */
public class MyNasaPictureRecyclerViewAdapter extends RecyclerView.Adapter<MyNasaPictureRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private int layoutPlantilla;
    private List<NasaPicture> mValues;
    private INasaPictureListener mListener;
    String urlToLoad;
    int errorToLoad;

    public MyNasaPictureRecyclerViewAdapter(Context ctx, int layoutPlantilla, List<NasaPicture> mValues) {
        this.ctx = ctx;
        this.layoutPlantilla = layoutPlantilla;
        this.mValues = mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_nasapicture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(holder.mItem.getUrl().contains("www.youtube")) {
            UrlToUrlThumbnail transformer = new UrlToUrlThumbnail(holder.mItem.getUrl());
            urlToLoad = transformer.urlToThumbnail();
            errorToLoad = R.drawable.ic_youtube_logo;
        } else {
            urlToLoad = holder.mItem.getUrl();
            errorToLoad = R.drawable.ic_no_image_loaded;
        }
        Glide
                .with(ctx)
                .load(urlToLoad)
                .error(Glide.with(ctx).load(errorToLoad))
                .thumbnail(Glide.with(ctx).load(R.drawable.loading_killer_whale_gif).centerCrop())
                .into(holder.ivPhoto);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mItem.getUrl().contains("https://www.youtube.com")){
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(holder.mItem.getUrl()));
                    ctx.startActivity(webIntent);
                } else {
                    Intent intent = new Intent(
                            ctx,
                            PhotoDetailsActivity.class
                    );
                    intent.putExtra("nasaPhotoSelected", holder.mItem.getUrl());
                    intent.putExtra("nasaTittleSelected", holder.mItem.getTitle());
                    intent.putExtra("nasaDescriptionSelected", holder.mItem.getExplanation());
                    intent.putExtra("nasaDateSelected", holder.mItem.getDate());
                    ctx.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivPhoto;
        public final TextView mContentView;
        public NasaPicture mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivPhoto = view.findViewById(R.id.imageViewPhotoHistoric);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

    }
}
