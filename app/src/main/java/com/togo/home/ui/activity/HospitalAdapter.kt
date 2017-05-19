package com.togo.home.ui.activity

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.togo.home.R
import com.togo.home.data.retrofit.response.PatientFirstPageModel
import kotlinx.android.synthetic.main.togo_home_cell.view.*

/**
 * Created by Yang Feng on 12/17/16.
 */

class HospitalAdapter(context: Context) : BaseAdapter<PatientFirstPageModel>() {

    // endregion

    override fun getItemViewType(position: Int): Int {
        return BaseAdapter.ITEM
    }

    override fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.togo_home_cell, parent, false)

        val holder = MovieViewHolder(v)

        holder.itemView.setOnClickListener {
            val adapterPos = holder.adapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(adapterPos, holder.itemView)
                }
            }
        }

        return holder
    }

    override fun bindHeaderViewHolder(viewHolder: RecyclerView.ViewHolder) {

    }

    override fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as MovieViewHolder

        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    // region Inner Classes
    class MovieViewHolder
    // endregion

    // region Constructors
    (view: View) : RecyclerView.ViewHolder(view) {
        // region Views
//        @BindView(R.id.coverPhoto)
//        var coverPhoto: ImageView? = null
//
//        @BindView(R.id.displayName)
//        var displayName: TextView? = null
//
//        @BindView(R.id.emergentLine)
//        var emergentLine: TextView? = null
//
//        @BindView(R.id.serviceLine)
//        var serviceLine: TextView? = null
//
//        init {
//            ButterKnife.bind(this, view)
//        }
        // endregion

        // region Helper Methods
        fun bind(model: PatientFirstPageModel) {
            if (!TextUtils.isEmpty(model.coverPhoto)) {
                Picasso.with(itemView.coverPhoto.context).load(model.coverPhoto).into(itemView.coverPhoto)
            }

            itemView.displayName.text = model.displayName
            itemView.displayName.text = model.displayName
            itemView.emergentLine.text = model.emergentLine
            itemView.serviceLine.text = model.serviceLine
        }
        // endregion
    }

    // endregion
}
