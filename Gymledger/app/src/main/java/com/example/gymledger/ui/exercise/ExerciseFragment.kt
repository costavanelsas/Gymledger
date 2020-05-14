package com.example.gymledger.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gymledger.R

/**
 * Created by Costa van Elsas on 14-5-2020.
 */

class ExerciseFragment : Fragment() {

    private lateinit var galleryViewModel: ExerciseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(ExerciseViewModel::class.java)
        val root = inflater.inflate(R.layout.exercise_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_exercise)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
