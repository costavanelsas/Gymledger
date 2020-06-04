package com.example.gymledger.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Costa van Elsas on 21-5-2020.
 */
@Parcelize
data class AddExercise(
    val naam: String = "",
    val beschrijving: String = ""
) : Parcelable