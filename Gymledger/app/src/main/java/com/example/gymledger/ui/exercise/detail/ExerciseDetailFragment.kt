package com.example.gymledger.ui.exercise.detail

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.gymledger.R
import com.example.gymledger.extention.observeNonNull
import com.example.gymledger.model.AddExercise
import com.example.gymledger.model.Exercise
import kotlinx.android.synthetic.main.fragment_exercise_detail.*
import kotlinx.android.synthetic.main.item_exercise.*

class ExerciseDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false)
    }

    private lateinit var exerciseDetailViewModel: ExerciseDetailViewModel
    private val args: ExerciseDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        requireActivity().actionBar?.show()
    }

    /**
     * Prepares the views inside this fragment.
     */
    private fun initViews(exercise: Exercise) {
        val roundingRadius = 5
        val thumbnailSize = 0.25f
        val reqOpt = RequestOptions.fitCenterTransform()
            .transform(RoundedCorners(roundingRadius))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(
                ivExercise2.width,
                ivExercise2.height
            )

        Glide.with(this)
            .load(exercise.image)
            .thumbnail(thumbnailSize)
            .apply(reqOpt)
            .placeholder(ColorDrawable(Color.WHITE))
            .into(ivExercise2)

        tvDetailNaam.text = exercise.naam
        tvContent.text = exercise.beschrijving

    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        exerciseDetailViewModel =
            ViewModelProvider(this).get(ExerciseDetailViewModel::class.java)
        exerciseDetailViewModel.initExercise(args.exercise)

        exerciseDetailViewModel.exercise.observeNonNull(viewLifecycleOwner, this::initViews)
    }
}