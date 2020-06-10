package com.example.gymledger.ui.measurements.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
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

const val ZERO = 0
const val HUNDRED = 100
const val MAX_DAYS = 31
const val MAX_MONTHS = 12
const val MIN_YEAR = 1900
const val MAX_YEAR = 9999

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

        //when the button is clicked link to addMeasurement()
        btnSaveMeasurements.setOnClickListener {
            addMeasurement()
        }
    }

    /**
     * get the list from the database and add it tot the list
     */
    private fun getListFromDatabase() {
        measurementViewModel = ViewModelProvider(this).get(MeasurementViewModel::class.java)

        measurementViewModel.measurements.observe(this, Observer { measurements ->
            this@AddMeasurement.measurementList.clear()
            this@AddMeasurement.measurementList.addAll(measurements)
            measurementAdapter.notifyDataSetChanged()
        })
    }

    /**
     * method for when the measurement is added
     */
    @SuppressLint("SimpleDateFormat")
    private fun addMeasurement() {
        //string for the date
        val concatenatedString = (etDay.text.toString() + "-" + etMonth.text.toString() + "-" + etYear.text.toString())

        //check if the fields are valid
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

    /**
     * validate all fields by calling the methods
     */
    private fun validateEmptyFields(): Boolean {
        return validateField(editTextWeight, "Weight")
                && validatePercent(editTextFatPercentage)
                && validateField(editTextFatPercentage, "fat percentage")
                && validateField(editTextImageMeasurement, "Image")
                && validatePercent(etMuscleMass)
                && validateField(etMuscleMass, "Muscle mass")
                && validateField(etWeightGoal, "weight goal")
                && validateField(etNotes, "Notes" )
                && validatePercent(etFatGoal)
                && validateField(etFatGoal, "Fat goal")
                && validateDay(etDay)
                && validateMonth(etMonth)
                && validateYear(etYear)
                && validateField(etDay, "date")
                && validateField(etMonth, "month")
                && validateField(etYear, "year")
    }

    /**
     * check if the fields are not empty
     */
    private fun validateField(theInput: EditText, fieldName: String): Boolean {
        if(theInput.text.toString().isBlank()){
            theInput.error = "Fill in a valid $fieldName"
            return false
        }
        return true
    }

    /**
     * check if the percentage fields is 0-100
     */
    private fun validatePercent(theInput: EditText): Boolean {
        if (theInput.text.toString().isBlank() ||
            theInput.text.toString().toInt() < ZERO ||
            theInput.text.toString().toInt() > HUNDRED) {
            theInput.error = "Fill in a valid percentage"
            return false
        }
        return true
    }

    /**
     * check if the year is a correct year
     */
    private fun validateYear(theInput: EditText): Boolean {
        if(theInput.text.toString().toInt() < MIN_YEAR || theInput.text.toString().toInt() > MAX_YEAR){
            theInput.error = "Fill in a valid year"
            return false
        }
        return true
    }

    /**
     * check if the month is a correct month
     */
    private fun validateMonth(theInput: EditText): Boolean {
        if(theInput.text.toString().toInt() < ZERO || theInput.text.toString().toInt() > MAX_MONTHS){
            theInput.error = "Fill in a valid month"
            return false
        }
        return true
    }

    /**
     * check if the day is a correct day
     */
    private fun validateDay(theInput: EditText): Boolean {
        if(theInput.text.toString().toInt() < ZERO || theInput.text.toString().toInt() > MAX_DAYS){
            theInput.error = "Fill in a valid day"
            return false
        }
        return true
    }
}