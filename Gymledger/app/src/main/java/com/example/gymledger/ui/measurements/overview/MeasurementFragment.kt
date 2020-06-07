package com.example.gymledger.ui.measurements.overview

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymledger.R
import com.example.gymledger.database.dao.MeasurementRepository
import com.example.gymledger.model.Measurement
import com.example.gymledger.ui.measurements.add.AddMeasurement
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.measurements.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MeasurementFragment : Fragment() {

    private val measurementList = arrayListOf<Measurement>()
    private val measurementAdapter =
        MeasurementAdapter(
            measurementList
        )
    private lateinit var measurementRepository: MeasurementRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var measurementViewModel: MeasurementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        measurementRepository = MeasurementRepository(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.measurements, container, false)
    }

    private fun initViews() {
        rvMeasurementList.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        rvMeasurementList.adapter = measurementAdapter
        rvMeasurementList.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvMeasurementList)
        getListFromDatabase()

        btnAddMeasurements.setOnClickListener{
            val intent = Intent(requireContext(), AddMeasurement::class.java)
            startActivity(intent)
        }

    }

    private fun getListFromDatabase() {
//        mainScope.launch {
//            val measurementList = withContext(Dispatchers.IO) {
//                measurementViewModel.getAllMeasurements()
//            }
//            this@MeasurementFragment.measurementList.clear()
//            this@MeasurementFragment.measurementList.addAll(measurementList)
//            this@MeasurementFragment.measurementAdapter.notifyDataSetChanged()
//        }

        measurementViewModel = ViewModelProvider(this).get(MeasurementViewModel::class.java)

        measurementViewModel.measurements.observe(viewLifecycleOwner, Observer { measurements ->
            this@MeasurementFragment.measurementList.clear()
            this@MeasurementFragment.measurementList.addAll(measurements)
            measurementAdapter.notifyDataSetChanged()
        })
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val measurementToDelete = measurementList[position]

                if(direction == ItemTouchHelper.LEFT) {
                    mainScope.launch {
                        withContext(Dispatchers.IO) {
                            measurementViewModel.deleteMeasurement(measurementToDelete)
                            Snackbar.make(viewHolder.itemView,
                                "Successfully deleted measurement", Snackbar.LENGTH_LONG)
                                .setAction("UNDO"){
                                    measurementViewModel.insertMeasurement(measurementToDelete)
                                }
                                .show()
                        }
                        getListFromDatabase()
                    }
                }
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun deleteAllMeasurements() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                measurementRepository.deleteAllMeasurements()
            }
            getListFromDatabase()
        }
    }

}