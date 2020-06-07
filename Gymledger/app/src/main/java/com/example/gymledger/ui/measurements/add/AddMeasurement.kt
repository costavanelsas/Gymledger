package com.example.gymledger.ui.measurements.add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import com.example.gymledger.R
import com.example.gymledger.database.dao.MeasurementRepository
import com.example.gymledger.model.Exercise
import com.example.gymledger.model.Measurement
import com.example.gymledger.ui.exercise.overview.ExerciseFragmentDirections
import com.example.gymledger.ui.measurements.overview.MeasurementAdapter
import com.example.gymledger.ui.measurements.overview.MeasurementFragment
import kotlinx.android.synthetic.main.add_exercise_fragment.*
import kotlinx.android.synthetic.main.fragment_measurement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Costa van Elsas on 6-6-2020.
 */
class AddMeasurement : AppCompatActivity() {

    private val measurementList = arrayListOf<Measurement>()
    private val measurementAdapter =
        MeasurementAdapter(
            measurementList
        )
    private lateinit var measurementRepository: MeasurementRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_measurement)
        supportActionBar?.title = getString(R.string.measurements)

        measurementRepository = MeasurementRepository(this)
        btnSaveMeasurements.setOnClickListener {
            addMeasurement()
            onSaveClick()
            val intent = Intent(this, MeasurementFragment::class.java)
            startActivity(intent)
        }
    }

    private fun getListFromDatabase() {
        mainScope.launch {
            val measurementList = withContext(Dispatchers.IO) {
                measurementRepository.getAllMeasurements()
            }
            this@AddMeasurement.measurementList.clear()
            this@AddMeasurement.measurementList.addAll(measurementList)
            this@AddMeasurement.measurementAdapter.notifyDataSetChanged()
        }
    }

    private fun addMeasurement() {
        if (validateFields()) {
            mainScope.launch {
                val measurement = Measurement(
                    weight = editTextWeight.text.toString().toInt(),
                    fat_percentage = editTextFatPercentage.text.toString().toInt() ,
                    muscle_mass = etMuscleMass.text.toString().toInt(),
                    weight_goal = etWeightGoal.text.toString().toInt(),
                    image = editTextImageMeasurement.text.toString(),
                    notes = etNotes.text.toString(),
                    fat_goal = etFatGoal.text.toString().toInt()
                )

                withContext(Dispatchers.IO) {
                    measurementRepository.insertMeasurement(measurement)
                }

                getListFromDatabase()
            }
        }
    }

    private fun validateFields(): Boolean {
        return if (editTextWeight.text.toString().isNotBlank() && editTextFatPercentage.text.toString().isNotBlank()) {
            true
        } else {
            Toast.makeText(this, "Please fill in the fields", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun onSaveClick() {
        Toast.makeText(this, "Your measurements are added!", Toast.LENGTH_SHORT).show()
    }
}