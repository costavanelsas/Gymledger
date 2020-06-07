package com.example.gymledger

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gymledger.database.dao.MeasurementDao
import com.example.gymledger.database.dao.MeasurementListRoomDatabase
import com.example.gymledger.database.dao.MeasurementRepository
import com.example.gymledger.model.Measurement
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

}
    @RunWith(AndroidJUnit4::class)
    class SimpleEntityReadWriteTest {
        private lateinit var userDao: MeasurementDao
        private lateinit var db: MeasurementListRoomDatabase

        @Before
        fun createDb() {
            val context = ApplicationProvider.getApplicationContext<Context>()
            db = Room.inMemoryDatabaseBuilder(
                context, MeasurementListRoomDatabase::class.java).build()
            userDao = db.measurementDao()
        }

        @After
        @Throws(IOException::class)
        fun closeDb() {
            db.close()
        }

        @Test
        @Throws(Exception::class)
        suspend fun writeUserAndReadInList() {
            userDao.insertMeasurement(Measurement(50,5,5,5,5,"asdasd","asdasd"))
        }
    }
