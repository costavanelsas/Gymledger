package com.example.gymledger.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymledger.R
import com.example.gymledger.model.Exercise
import kotlinx.android.synthetic.main.exercise_fragment.*

/**
 * Created by Costa van Elsas on 14-5-2020.
 */

class ExerciseFragment : Fragment() {

    private val exercise = arrayListOf<Exercise>()
    private val exerciseAdapter = ExerciseAdapter(exercise)
    private lateinit var exerciseViewModel: ExerciseViewModel
    private var recycler_view: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exercise_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)

        initViewModel()
        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
//        rvExercise.layoutManager = LinearLayoutManager(context)
//        rvExercise.adapter = exerciseAdapter

        recycler_view = view?.findViewById(R.id.rvExercise)
        recycler_view?.layoutManager = LinearLayoutManager(context)
        recycler_view?.adapter = exerciseAdapter
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        exerciseViewModel = ViewModelProvider(this@ExerciseFragment).get(ExerciseViewModel::class.java)

        val liveData = exerciseViewModel.getAll()

        liveData.observe(viewLifecycleOwner, Observer { list ->
            if (list != null) {
                exercise.clear()
                exercise.addAll(list)

                pbActivity.visibility = View.GONE
                exerciseAdapter.notifyDataSetChanged()
            }
        })
    }

}
