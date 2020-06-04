package com.example.gymledger.ui.exercise.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gymledger.model.Exercise

/**
 * Created by Costa van Elsas on 4-6-2020.
 */
class ExerciseDetailViewModel : ViewModel() {
    val exercise = MutableLiveData<Exercise>()

    /**
     * Keeps track of currently selected department for the detail view.
     */
    fun initExercise(exercise: Exercise) {
        this.exercise.value = exercise
    }
}