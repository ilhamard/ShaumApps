package com.dev.shaumapps.ui.hadis

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dev.shaumapps.databinding.ActivityHadisBinding

class HadisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHadisBinding
    private lateinit var viewModel: HadisViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHadisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Hadits"

        viewModel = ViewModelProvider(this)[HadisViewModel::class.java]

        viewModel.getRandomHadis()

        viewModel.hadisRespone.observe(this) { data ->
            binding.apply {
                tvArti.visibility = View.VISIBLE
                tvRiwayat.visibility = View.VISIBLE
                tvArti.text = data.data.contents.id
                tvRiwayat.text = data.data.name
            }
        }

        viewModel.isError.observe(this) {
            binding.tvArti.visibility = View.GONE
            binding.tvRiwayat.visibility = View.GONE
        }

        binding.btnPrev.setOnClickListener {
            viewModel.getRandomHadis()
        }

        binding.btnNext.setOnClickListener {
            viewModel.getRandomHadis()
        }

        viewModel.isLoading.observe(this) {
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}