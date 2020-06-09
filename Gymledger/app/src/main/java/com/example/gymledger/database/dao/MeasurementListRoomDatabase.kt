package com.example.gymledger.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gymledger.database.Converters
import com.example.gymledger.model.ExercisePersonalInfo
import com.example.gymledger.model.Measurement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by Costa van Elsas on 5-6-2020.
 */
@Database(entities = [Measurement::class], version = 5)
@TypeConverters(Converters::class)
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
                            Room.databaseBuilder(context.applicationContext,
                                MeasurementListRoomDatabase::class.java, DATABASE_NAME)
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return measurementListRoomDatabaseinstance
        }
    }

}