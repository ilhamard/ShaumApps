package com.dev.shaumapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dev.shaumapps.databinding.ActivityDetailKutipanBinding

class DetailKutipanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKutipanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKutipanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data  = intent.getStringExtra(EXTRA_KUTIPAN)
        Log.d("DetailKutpan", "cek Hasil $data" )
        data?.toInt()?.let { binding.imgDetailKutipan.setImageResource(it) }
    }

    companion object {
        const val EXTRA_KUTIPAN = "EXTRA_KUTIPAN"
    }
}