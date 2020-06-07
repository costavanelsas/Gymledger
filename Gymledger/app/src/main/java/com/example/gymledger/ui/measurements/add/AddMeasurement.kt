package com.example.gymledger.ui.measurements.add

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.TypeConverters
import com.example.gymledger.R
import com.example.gymledger.database.Converters
import com.example.gymledger.database.dao.MeasurementRepository
import com.example.gymledger.model.Measurement
import com.example.gymledger.ui.measurements.overview.MeasurementAdapter
import com.example.gymledger.ui.measurements.overview.MeasurementFragment
import com.example.gymledger.ui.measurements.overview.MeasurementViewModel
import kotlinx.android.synthetic.main.fragment_measurement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

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
            onSaveClick()
            startActivity()
        }
    }

    private fun startActivity() {
        val intent = Intent(this, MeasurementFragment::class.java)
        startActivity(intent)
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
        }
    }

    private fun validateEmptyFields(): Boolean {
        if (editTextWeight.text.toString().isBlank()) {
            Toast.makeText(this,"Please fill in a weight"
                , Toast.LENGTH_SHORT).show()
            return false
        }

        if (editTextFatPercentage.text.toString().isBlank()) {
            Toast.makeText(this,"Please fill in a fat percentage"
                , Toast.LENGTH_SHORT).show()
            return false
        }

        if (etDay.text.toString().isBlank() || etMonth.text.toString().isBlank() ||
            etYear.text.toString().isBlank()) {
            Toast.makeText(this,"Please fill in a date"
                , Toast.LENGTH_SHORT).show()
            return false
        }

        if (editTextImageMeasurement.text.toString().isBlank()) {
            Toast.makeText(this,"Please fill in an image"
                , Toast.LENGTH_SHORT).show()
            return false
        }

        if (etNotes.text.toString().isBlank()) {
            Toast.makeText(this,"Please fill in some notes"
                , Toast.LENGTH_SHORT).show()
            return false
        }

        if (etWeightGoal.text.toString().isBlank()) {
            Toast.makeText(this,"Please fill in a weight goal"
                , Toast.LENGTH_SHORT).show()
            return false
        }

        if (etFatGoal.text.toString().isBlank()) {
            Toast.makeText(this,"Please fill in a fat goal"
                , Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun onSaveClick() {
        Toast.makeText(this, "Your measurements are added!", Toast.LENGTH_SHORT).show()
    }
}