package com.example.gymledger.ui.measurements.add

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.TypeConverters
import com.example.gymledger.R
import com.example.gymledger.database.Converters
import com.example.gymledger.model.Measurement
import com.example.gymledger.ui.measurements.overview.MeasurementAdapter
import com.example.gymledger.ui.measurements.overview.MeasurementViewModel
import kotlinx.android.synthetic.main.fragment_measurement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

/**
 * Created by Costa van Elsas on 6-6-2020.
 */
@TypeConverters(Converters::class)
class AddMeasurement : AppCompatActivity() {

    private val measurementList = arrayListOf<Measurement>()
    private val measurementAdapter =
        MeasurementAdapter(
            measurementList
        )
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var measurementViewModel: MeasurementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_measurement)
        supportActionBar?.title = getString(R.string.measurements)

        measurementViewModel = ViewModelProvider(this).get(MeasurementViewModel::class.java)
        btnSaveMeasurements.setOnClickListener {
            addMeasurement()
        }
    }

    private fun getListFromDatabase() {
        measurementViewModel = ViewModelProvider(this).get(MeasurementViewModel::class.java)

        measurementViewModel.measurements.observe(this, Observer { measurements ->
            this@AddMeasurement.measurementList.clear()
            this@AddMeasurement.measurementList.addAll(measurements)
            measurementAdapter.notifyDataSetChanged()
        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun addMeasurement() {
        val concatenatedString = (etDay.text.toString() + "-" + etMonth.text.toString() + "-" + etYear.text.toString())
        if (validateEmptyFields()) {
            mainScope.launch {
                val date = SimpleDateFormat("dd-MM-yyyy")
                date.isLenient = false
                val parsedDate = date.parse(concatenatedString)

                val measurement = Measurement(
                    weight = editTextWeight.text.toString().toInt(),
                    fat_percentage = editTextFatPercentage.text.toString().toInt() ,
                    muscle_mass = etMuscleMass.text.toString().toInt(),
                    weight_goal = etWeightGoal.text.toString().toInt(),
                    image = editTextImageMeasurement.text.toString(),
                    notes = etNotes.text.toString(),
                    fat_goal = etFatGoal.text.toString().toInt(),
                    dateAdded = parsedDate
                )

                withContext(Dispatchers.IO) {
                    measurementViewModel.insertMeasurement(measurement)
                }

                getListFromDatabase()
            }

            finish()
        } else {
            return
        }
    }

    private fun validateEmptyFields(): Boolean {
        val zero = 0
        val hundred = 100
        val maxDays = 31
        val maxMonths = 12
        val minYear = 1900
        val maxYear = 9999

        if (editTextWeight.text.toString().isBlank()) {
            editTextWeight.error = "Please fill in a weight"
            return false
        }

        if (editTextFatPercentage.text.toString().isBlank() ||
            editTextFatPercentage.text.toString().toInt() < zero ||
            editTextFatPercentage.text.toString().toInt() > hundred
        ) {
            editTextFatPercentage.error = "Please fill in a fat percentage: 0-100%"
            return false
        }

        if (editTextImageMeasurement.text.toString().isBlank()) {
            editTextImageMeasurement.error = "Please fill in an image"
            return false
        }

        if (etNotes.text.toString().isBlank()) {
            etNotes.error = "Please fill in some notes"
            return false
        }

        if (etWeightGoal.text.toString().isBlank()) {
            etWeightGoal.error = "Please fill in a weight goal"
            return false
        }

        if (etFatGoal.text.toString().isBlank()|| etFatGoal.text.toString().toInt() < zero ||
            etFatGoal.text.toString().toInt() > hundred) {
            etFatGoal.error = "Please fill in a fat percentage goal"
            return false
        }

        if (etDay.text.toString().isBlank() || etMonth.text.toString().isBlank() ||
            etYear.text.toString().isBlank()) {
            etDay.error = "Please fill in a day"
            etMonth.error = "Please fill in a month"
            etYear.error = "Please fill in a year"
            return false
        }

        if(etDay.text.toString().toInt() < zero || etDay.text.toString().toInt() > maxDays){
            etDay.error = "Fill in a valid day"
            return false
        }

        if(etMonth.text.toString().toInt() < zero || etMonth.text.toString().toInt() > maxMonths){
            etMonth.error = "Fill in a valid month"
            return false
        }

        if(etYear.text.toString().toInt() < minYear || etYear.text.toString().toInt() > maxYear){
            etYear.error = "Fill in a valid year"
            return false
        }

        return true
    }
}