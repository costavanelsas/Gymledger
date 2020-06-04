package com.example.gymledger.ui.exercise.overview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.gymledger.R
import com.example.gymledger.model.Exercise
import com.example.gymledger.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_exercise.view.*

/**
 * Created by Costa van Elsas on 20-5-2020.
 */
class ExerciseAdapter(
    override val items: List<Exercise>,
    override val clickListener: (Exercise) -> Unit
) : AbstractAdapter<Exercise>(items, clickListener) {


    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<Exercise>.ViewHolder(itemView) {

        override fun bind(item: Exercise) {
            itemView.tvExercise.text = item.naam
            itemView.cvExercise.setOnClickListener { clickListener(item) }

            Glide.with(itemView.context).load(item.image).into(itemView.ivExercise)
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<Exercise>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        )
    }

}