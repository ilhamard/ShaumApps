package com.dev.dzikirkita.ui.catatan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.dzikirkita.R
import com.dev.dzikirkita.databinding.FragmentCatatanBinding
import com.dev.dzikirkita.util.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CatatanFragment : Fragment() {
    private var _binding: FragmentCatatanBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvCatatanFrag: CatatanAdapter
    private lateinit var viewModel: CatatanViewModel
    private lateinit var btnAdd: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCatatanBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.myToolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Catatan"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAdd = requireActivity().findViewById(R.id.fab_add_catatan)

        coloringIconFab()

        rvCatatanFrag = CatatanAdapter()

        viewModel = obtainViewModel(this@CatatanFragment)
        viewModel.getAllCatatan().observe(viewLifecycleOwner) { listCatatan ->
            rvCatatanFrag.setListCatatan(listCatatan)
        }

        btnAdd.setOnClickListener {
            val intent = Intent(requireContext(), CatatanAddUpdateActivity::class.java)
            startActivity(intent)
        }

        setRecyclerCatatan()
    }

    override fun onResume() {
        super.onResume()

        btnAdd.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()

        btnAdd.visibility = View.GONE
    }

    private fun coloringIconFab() {
        val csl = ContextCompat.getColorStateList(requireContext(), R.color.white)
        val fab = btnAdd
        val drawable = fab.drawable?.mutate()
        drawable?.let {
            DrawableCompat.setTintList(it, csl)
            fab.setImageDrawable(it)
        }
    }

    private fun setRecyclerCatatan() {
        rvCatatanFrag = CatatanAdapter()
        binding.rvCatatan.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = rvCatatanFrag
        }
    }

    private fun obtainViewModel(fragment: Fragment): CatatanViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory)[CatatanViewModel::class.java]
    }
}