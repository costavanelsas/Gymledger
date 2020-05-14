package com.example.gymledger.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Costa van Elsas on 14-5-2020.
 */
class ExerciseViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is exercise Fragment"
    }
    val text: LiveData<String> = _text
}