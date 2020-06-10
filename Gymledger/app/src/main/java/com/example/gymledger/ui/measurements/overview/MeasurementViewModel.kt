package com.example.gymledger.ui.measurements.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gymledger.database.dao.MeasurementRepository
import com.example.gymledger.model.Measurement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Costa van Elsas on 6-6-2020.
 */
class MeasurementViewModel(application: Application) : AndroidViewModel(application){

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val measurementRepository = MeasurementRepository(application.applicationContext)

    val measurements: LiveData<List<Measurement>> = measurementRepository.getAllMeasurements()

    /**
     * insert a measurement
     */
    fun insertMeasurement(measurement: Measurement) {
        ioScope.launch {
            measurementRepository.insertMeasurement(measurement)
        }
    }

    /**
     * Delete a single measurement
     */
    fun deleteMeasurement(measurement: Measurement) {
        ioScope.launch {
            measurementRepository.deleteMeasurement(measurement)
        }
    }

    /**
     * Delete all measurements
     */
    fun deleteAllMeasurements() {
        ioScope.launch {
            measurementRepository.deleteAllMeasurements()
        }
    }
}