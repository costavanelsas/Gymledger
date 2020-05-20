package com.example.gymledger.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Costa van Elsas on 20-5-2020.

 * A set of data abou texersise used in the exersise fragment.
 */

data class Exercise (
    val naam: String = "",
    val beschrijving: String = "",
    val thumbnail: String = "",
    val image: String = ""
)