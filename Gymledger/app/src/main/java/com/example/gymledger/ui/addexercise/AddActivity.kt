package com.example.gymledger.ui.addexercise

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymledger.R
import com.example.gymledger.model.AddExercise
import kotlinx.android.synthetic.main.add_activity.*

/**
 * Created by Costa van Elsas on 21-5-2020.
 */
class AddActivity : AppCompatActivity() {

    private val add = arrayListOf<AddExercise>()
    private val addAdapter = AddAdapter(add)
    private lateinit var addViewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvAdd.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvAdd.adapter = addAdapter
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        addViewModel = ViewModelProvider(this@AddActivity).get(AddViewModel::class.java)

        val liveData = addViewModel.getAll()

        liveData.observe(this, Observer { list ->
            if (list != null) {
                add.clear()
                add.addAll(list)

                pbActivity.visibility = View.GONE
                addAdapter.notifyDataSetChanged()
            }
        })
    }
}