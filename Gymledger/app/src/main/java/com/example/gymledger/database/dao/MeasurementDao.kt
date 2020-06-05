package com.example.gymledger.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gymledger.model.Measurement

/**
 * Created by Costa van Elsas on 5-6-2020.
 */
@Dao
interface MeasurementDao {

    @Query("SELECT * FROM measurement_table")
    suspend fun getAllMeasurements(): List<Measurement>

    @Insert
    suspend fun insertMeasurement(measurement: Measurement)

    @Delete
    suspend fun deleteMeasurement(measurement: Measurement)

    @Query("DELETE FROM measurement_table")
    suspend fun deleteAllMeasurements()
}