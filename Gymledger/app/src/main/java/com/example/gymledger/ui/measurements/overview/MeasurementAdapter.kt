package com.example.gymledger.ui.measurements.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymledger.R
import com.example.gymledger.model.Measurement
import kotlinx.android.synthetic.main.item_exercise.view.*
import kotlinx.android.synthetic.main.item_measurement.view.*

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
        fun bind(item: Measurement) {
            itemView.tvMeasurementKg.text = item.weight.toString()
            itemView.tvMeasurement.text = item.fat_percentage.toString()

            Glide.with(itemView.context).load(item.image).into(itemView.ivMeasurement)
        }
    }


}