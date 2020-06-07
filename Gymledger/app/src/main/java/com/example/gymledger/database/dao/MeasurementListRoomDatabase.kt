package com.example.gymledger.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gymledger.model.Measurement

/**
 * Created by Costa van Elsas on 5-6-2020.
 */
@Database(entities = [Measurement::class], version = 2)
abstract class MeasurementListRoomDatabase : RoomDatabase() {

    abstract fun measurementDao(): MeasurementDao

    companion object {
        private const val DATABASE_NAME = "MEASUREMENTS_DATABASE"

        @Volatile
        private var measurementListRoomDatabaseinstance: MeasurementListRoomDatabase? = null

        fun getDatabase(context: Context): MeasurementListRoomDatabase? {
            if (measurementListRoomDatabaseinstance == null) {
                synchronized(MeasurementListRoomDatabase::class.java) {
                    if (measurementListRoomDatabaseinstance == null) {
                        measurementListRoomDatabaseinstance =
                            Room.databaseBuilder(context.applicationContext,MeasurementListRoomDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return measurementListRoomDatabaseinstance
        }
    }

}