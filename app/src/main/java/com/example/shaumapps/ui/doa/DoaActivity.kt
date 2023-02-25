package com.example.shaumapps.ui.doa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.shaumapps.data.remote.response.DoaHarianResponse
import com.example.shaumapps.data.remote.response.DoaHarianResponseItem
import com.example.shaumapps.data.remote.retrofit.ApiConfig
import com.example.shaumapps.databinding.ActivityDoaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoaBinding
    private lateinit var viewModel: DoaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DoaViewModel::class.java]

        viewModel.doaRespone.observe(this){
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
    }
}