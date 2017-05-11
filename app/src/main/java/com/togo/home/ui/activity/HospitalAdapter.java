package com.togo.home.ui.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.togo.home.R;
import com.togo.home.data.retrofit.response.PatientFirstPageModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 12/17/16.
 */

public class HospitalAdapter extends BaseAdapter<PatientFirstPageModel> {

    public HospitalAdapter(Context context) {
    }

    // endregion

    @Override
    public int getItemViewType(int position) {
        return ITEM;
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.togo_home_cell, parent, false);

        final MovieViewHolder holder = new MovieViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(adapterPos, holder.itemView);
                    }
                }
            }
        });

        return holder;
    }

    @Override
    protected void bindHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final MovieViewHolder holder = (MovieViewHolder) viewHolder;

        final PatientFirstPageModel movie = getItem(position);
        if (movie != null) {
            holder.bind(movie);
        }
    }

    // region Inner Classes
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        // region Views
        @BindView(R.id.coverPhoto)
        protected ImageView coverPhoto;

        @BindView(R.id.displayName)
        protected TextView displayName;

        @BindView(R.id.emergentLine)
        protected TextView emergentLine;

        @BindView(R.id.serviceLine)
        protected TextView serviceLine;

        // endregion

        // region Constructors
        public MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        // endregion

        // region Helper Methods
        private void bind(PatientFirstPageModel model){
            if (!TextUtils.isEmpty(model.getCoverPhoto())) {
                Picasso.with(coverPhoto.getContext()).load(model.getCoverPhoto()).into(coverPhoto);
            }

            displayName.setText(model.getDisplayName());
            emergentLine.setText(model.getEmergentLine());
            serviceLine.setText(model.getServiceLine());
        }
        // endregion
    }

    // endregion
}
