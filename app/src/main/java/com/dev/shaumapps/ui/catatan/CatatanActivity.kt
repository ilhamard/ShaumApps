package com.dev.shaumapps.ui.catatan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.shaumapps.CatatanAddUpdateActivity
import com.dev.shaumapps.R
import com.dev.shaumapps.databinding.ActivityCatatanBinding
import com.dev.shaumapps.util.ViewModelFactory

class CatatanActivity : AppCompatActivity() {

    private var _binding: ActivityCatatanBinding? = null
    private val binding get() = _binding

    private lateinit var adapter: CatatanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCatatanBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val catatanViewModel = obtainViewModel(this@CatatanActivity)
        catatanViewModel.getAllCatatan().observe(this) { listCatatan ->
            if (listCatatan != null) {
                adapter.setListCatatan(listCatatan)
            }
        }

        adapter = CatatanAdapter()

        binding?.rvCatatan?.layoutManager = LinearLayoutManager(this)
        binding?.rvCatatan?.setHasFixedSize(true)
        binding?.rvCatatan?.adapter = adapter



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): CatatanViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(CatatanViewModel::class.java)
    }



}