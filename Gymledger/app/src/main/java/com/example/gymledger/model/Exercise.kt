package com.example.gymledger.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Costa van Elsas on 20-5-2020.

 * A set of data about exersise used in the exersise fragment.
 */
class Exercise (
    val naam: String = "",
    val beschrijving: String = "",
    val image: String = ""
)