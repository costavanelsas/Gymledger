package com.example.gymledger.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gymledger.model.ExercisePersonalInfo
import com.example.gymledger.model.Measurement

/**
 * Created by Costa van Elsas on 5-6-2020.
 */
@Dao
interface MeasurementDao {

    @Query("SELECT * FROM measurement_table")
    fun getAllMeasurements(): LiveData<List<Measurement>>

    @Insert
    suspend fun insertMeasurement(measurement: Measurement)

    @Delete
    suspend fun deleteMeasurement(measurement: Measurement)

    @Query("DELETE FROM measurement_table")
    suspend fun deleteAllMeasurements()

}