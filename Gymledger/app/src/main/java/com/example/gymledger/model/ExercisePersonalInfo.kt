package com.example.gymledger.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by Costa van Elsas on 21-5-2020.
 */
@Parcelize
@Entity(tableName = "personal_info_table")
data class ExercisePersonalInfo(
    @ColumnInfo(name = "lastupdated")
    var lastUpdated: Date,

    @ColumnInfo(name = "text")
    var text: String,

    @PrimaryKey var id: Long? = null
) : Parcelable