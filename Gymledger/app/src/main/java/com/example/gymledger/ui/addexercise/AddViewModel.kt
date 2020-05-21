package com.example.gymledger.ui.addexercise

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.gymledger.database.FirebaseQueryLiveData
import com.example.gymledger.model.AddExercise
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Costa van Elsas on 21-5-2020.
 */
class AddViewModel : ViewModel() {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val liveData: FirebaseQueryLiveData = FirebaseQueryLiveData(DATABASE_REF)
    private val addExerciseLiveData = MediatorLiveData<List<AddExercise>>()

    companion object {
        private const val DATABASE_KEY = "exercise"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        addExerciseLiveData.addSource(
            liveData
        ) { dataSnapshot ->
            if (dataSnapshot != null) {
                mainScope.launch {
                    val list = ArrayList<AddExercise>()

                    dataSnapshot.children.forEach {
                        val item: AddExercise? = it.getValue(AddExercise::class.java)

                        if (item != null)
                            list.add(item)
                    }

                    addExerciseLiveData.postValue(list)
                }
            } else {
                addExerciseLiveData.setValue(arrayListOf())
            }
        }
    }

    @NonNull
    fun getAll(): LiveData<List<AddExercise>> {
        return addExerciseLiveData
    }
}