package com.dev.shaumapps.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.shaumapps.CatatanAddUpdateActivity
import com.dev.shaumapps.R
import com.dev.shaumapps.databinding.FragmentCatatanBinding
import com.dev.shaumapps.ui.catatan.CatatanAdapter
import com.dev.shaumapps.ui.catatan.CatatanViewModel
import com.dev.shaumapps.util.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CatatanFragment : Fragment() {
    private var _binding: FragmentCatatanBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CatatanAdapter

    private lateinit var viewModel: CatatanViewModel
    private lateinit var btnAdd: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCatatanBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.myToolbar)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAdd = requireActivity().findViewById(R.id.btnAdd)

        adapter = CatatanAdapter()

        binding.rvCatatan.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCatatan.setHasFixedSize(true)
        binding.rvCatatan.adapter = adapter

        val catatanViewModel = obtainViewModel(this@CatatanFragment)
        catatanViewModel.getAllCatatan().observe(requireActivity()) { listCatatan ->
            if (listCatatan != null) {
                adapter.setListCatatan(listCatatan)
            }
        }

        binding.btnAdd.setOnClickListener {
            if (view.id == R.id.fab_add){
                val intent = Intent(requireContext(), CatatanAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainViewModel(fragment: Fragment): CatatanViewModel{
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory)[CatatanViewModel::class.java]
    }

//    private fun setRecyclerCatatan(){
//        rvCatatan = CatatanAdapter(this)
//        binding.rvCatatan.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
//            adapter = rvCatatan
//        }
//    }




    companion object{
        const val ADD_CATATAN_RESULT_OK = Activity.RESULT_FIRST_USER
        const val UPDATE_CATATAN_RESULT_OK = Activity.RESULT_FIRST_USER + 1
    }
}