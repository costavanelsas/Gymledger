package com.example.gymledger.ui.exercise

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gymledger.database.FirebaseQueryLiveData
import com.example.gymledger.model.Exercise
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Costa van Elsas on 14-5-2020.
 */
class ExerciseViewModel : ViewModel() {

    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val liveData: FirebaseQueryLiveData = FirebaseQueryLiveData(DATABASE_REF)
    private val exerciseLiveData = MediatorLiveData<List<Exercise>>()

    companion object {
        private const val DATABASE_KEY = "exercise"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }


    init {
        exerciseLiveData.addSource(
            liveData
        ) { dataSnapshot ->
            if (dataSnapshot != null) {
                mainScope.launch {
                    val list = ArrayList<Exercise>()

                    dataSnapshot.children.forEach {
                        val item: Exercise? = it.getValue(Exercise::class.java)

                        if (item != null)
                            list.add(item)
                    }

                    exerciseLiveData.postValue(list)
                }
            } else {
                exerciseLiveData.setValue(arrayListOf())
            }
        }
    }

    @NonNull
    fun getAll(): LiveData<List<Exercise>> {
        return exerciseLiveData
    }
}