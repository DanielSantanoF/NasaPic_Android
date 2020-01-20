package com.dsantano.nasapic;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dsantano.nasapic.api.NasaPicture;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NasaPicture} and makes a call to the
 * specified {@link INasaPictureListener}.
 */
public class MyNasaPictureRecyclerViewAdapter extends RecyclerView.Adapter<MyNasaPictureRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private int layoutPlantilla;
    private List<NasaPicture> mValues;

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
        Glide
                .with(ctx)
                .load(holder.mItem.getUrl())
                .thumbnail(Glide.with(ctx).load(R.drawable.loading_killer_whale_gif).centerCrop())
                .into(holder.ivPhoto);
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
