package com.example.gymledger.ui.addexercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gymledger.R
import com.example.gymledger.model.AddExercise
import com.example.gymledger.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_add.view.*

/**
 * Created by Costa van Elsas on 21-5-2020.
 */
class AddAdapter(
    override val items: List<AddExercise>
) : AbstractAdapter<AddExercise>(items) {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<AddExercise>.ViewHolder(itemView) {

        override fun bind(item: AddExercise) {
            itemView.tvName.text = item.naam
            itemView.tvDescription.text = item.beschrijving
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<AddExercise>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_add, parent, false)
        )
    }

}