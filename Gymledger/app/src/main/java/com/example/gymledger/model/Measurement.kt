package com.example.gymledger.model

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Costa van Elsas on 5-6-2020.
 */
@Entity(tableName = "measurement_table")
data class Measurement (
    @ColumnInfo(name = "weight")
    var weight: Float,

    @ColumnInfo(name = "fat_percentage")
    var fat_percentage: Float,

    @ColumnInfo(name = "muscle_mass")
    var muscle_mass: Float,

    @ColumnInfo(name = "weight_goal")
    var weight_goal: Float,

    @ColumnInfo(name = "fat_goal")
    var fat_goal: Float,

    @ColumnInfo(name = "notes")
    var notes: String,

    @ColumnInfo(name = "image")
    var image: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

)