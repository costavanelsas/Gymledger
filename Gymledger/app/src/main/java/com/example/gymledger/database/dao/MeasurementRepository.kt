package com.example.gymledger.database.dao

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.gymledger.model.Measurement

/**
 * Created by Costa van Elsas on 5-6-2020.
 */
class MeasurementRepository (context: Context){

    private val measurementDao: MeasurementDao

    init {
        val database = MeasurementListRoomDatabase.getDatabase(context)
        measurementDao = database!!.measurementDao()
    }

    fun getAllMeasurements(): LiveData<List<Measurement>> = measurementDao.getAllMeasurements()

    suspend fun insertMeasurement(measurement: Measurement) = measurementDao.insertMeasurement(measurement)

    suspend fun deleteMeasurement(measurement: Measurement) = measurementDao.deleteMeasurement(measurement)

    suspend fun deleteAllMeasurements() = measurementDao.deleteAllMeasurements()

    fun getMeasurements(): LiveData<List<Measurement>> {
        return measurementDao.getAllMeasurements()
    }

}