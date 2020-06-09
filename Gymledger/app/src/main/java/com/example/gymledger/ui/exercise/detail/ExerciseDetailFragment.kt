package com.example.gymledger.ui.exercise.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.solver.widgets.Snapshot
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.gymledger.R
import com.example.gymledger.extention.observeNonNull
import com.example.gymledger.model.Exercise
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_exercise_detail.*


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        requireActivity().actionBar?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_measurements, menu)
        super.onCreateOptionsMenu(menu, inflater)
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

    fun deleteData(){
        val ref = FirebaseDatabase.getInstance().getReference("exercise")
        //val id = ref.child("exercise").push().key
        //val idQuery: Query = ref.child(id!!)
        val exercise = exerciseDetailViewModel.exercise

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (idSnapshot in dataSnapshot.children) {

                    val exerciseDb = idSnapshot.getValue(Exercise::class.java)

                    if (exerciseDb != null && exerciseDb == exercise.value) {
                        val keyToDelete = idSnapshot.key
                        ref.child(keyToDelete!!).removeValue()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException())
            }
        })
//        val ref = FirebaseDatabase.getInstance().getReference("exercise").child(id.toString())
//        val id = ref.key
//        ref.removeValue(id)
    }

//    private var firebaseData = FirebaseDatabase.getInstance().reference
//    fun removeItem() {
//            firebaseData
//                .child("exercise")
//                .child()
//                .setValue(null)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_icon -> {
                    deleteData()
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}