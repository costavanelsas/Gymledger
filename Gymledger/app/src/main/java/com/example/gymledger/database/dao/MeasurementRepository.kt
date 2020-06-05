package com.example.gymledger.database.dao

import android.content.Context
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

    suspend fun getAllMeasurements(): List<Measurement> = measurementDao.getAllMeasurements()

    suspend fun insertMeasurement(measurement: Measurement) = measurementDao.insertMeasurement(measurement)

    suspend fun deleteMeasurement(measurement: Measurement) = measurementDao.deleteMeasurement(measurement)

    suspend fun deleteAllMeasurements() = measurementDao.deleteAllMeasurements()
}