package com.example.gymledger.ui.measurements.overview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymledger.R
import com.example.gymledger.model.Measurement
import kotlinx.android.synthetic.main.item_measurement.view.*
import java.text.SimpleDateFormat

/**
 * Created by Costa van Elsas on 5-6-2020.
 */
class MeasurementAdapter(private val measurements: List<Measurement>) : RecyclerView.Adapter<MeasurementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_measurement, parent, false)
        )
    }

    override fun getItemCount(): Int = measurements.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(measurements[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: Measurement) {

            val releaseDate = SimpleDateFormat("dd MMMM yyyy").format(item.dateAdded)

            Glide.with(itemView.context).load(item.image).into(itemView.ivMeasurement)

            itemView.tvMeasurementWeight.text = item.weight.toString() + " KG"
            itemView.tvMeasurementFatPercent.text = item.fat_percentage.toString() + " %"
            itemView.tvMusclemassCv2.text = item.muscle_mass.toString() + " %"
            itemView.tvMusclemassKgGoalCvBody.text = item.weight_goal.toString() + " KG"
            itemView.tvMeasurementFatGoalBody.text = item.fat_goal.toString() + " %"
            itemView.tvMeasurementNotesBody.text = item.notes
            itemView.tvDateAdded.text = releaseDate

        }
    }


}