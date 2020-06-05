package com.example.gymledger.ui.exercise.add

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymledger.R
import com.example.gymledger.model.Exercise
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.add_exercise_fragment.*

/**
 * Created by Costa van Elsas on 5-6-2020.
 */
class AddExercise : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_exercise_fragment)
        supportActionBar?.title = getString(R.string.add_exercise)

        buttonPost.setOnClickListener{
            postData()
        }

    }

    private fun postData(){
        val name = editTextTextPersonName.text.toString().trim()
        val description = editTextDescription.text.toString().trim()
        val image = editTextImage.text.toString().trim()

        if(name.isEmpty()){
            editTextTextPersonName.error = "Please fill in a name"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("exercise")
        val exerciseId = ref.push().key
        val postData = Exercise(name, description, image)

        if (exerciseId != null) {
            ref.child(exerciseId).setValue(postData).addOnCompleteListener {
                Toast.makeText(applicationContext, "Exercise is toegevoegd", Toast.LENGTH_LONG).show()
            }
        }


    }

}