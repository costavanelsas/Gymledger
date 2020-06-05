package com.example.gymledger.model

import android.os.ParcelUuid
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Costa van Elsas on 20-5-2020.

 * A set of data about exercise used in the exercise fragment.
 */
@Parcelize
data class Exercise(
    val naam: String = "",
    val beschrijving: String = "",
    val image: String = ""
) : Parcelable