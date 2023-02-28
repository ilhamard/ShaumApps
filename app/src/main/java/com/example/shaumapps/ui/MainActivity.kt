package com.example.shaumapps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shaumapps.databinding.ActivityMainBinding
import com.example.shaumapps.ui.kalender.KalenderActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnMasuk.setOnClickListener {
            val intent = Intent(this@MainActivity, KalenderActivity::class.java)
            startActivity(intent)
        }
    }
}