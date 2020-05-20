package com.example.gymledger.ui.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gymledger.R
import com.example.gymledger.model.Exercise
import com.example.gymledger.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_exercise.view.*

/**
 * Created by Costa van Elsas on 20-5-2020.
 */
class ExerciseAdapter(
    override val items: List<Exercise>
) : AbstractAdapter<Exercise>(items) {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<Exercise>.ViewHolder(itemView) {

        override fun bind(item: Exercise) {
            itemView.tvExercise.text = item.naam
            itemView.tvBeschrijving.text = item.beschrijving
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