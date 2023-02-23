package com.example.shaumapps

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shaumapps.kalender.ListTanggalAdapter
import com.example.shaumapps.kalender.Tanggal

class KalenderActivity : AppCompatActivity() {
    private lateinit var rvTanggal: RecyclerView
    private val list = ArrayList<Tanggal>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kalender)

        rvTanggal = findViewById(R.id.rv_tanggal)
        rvTanggal.setHasFixedSize(true)

        list.addAll(getTanggalList())
        ShowTanggal()

    }

    private fun getTanggalList(): ArrayList<Tanggal>{
        val dataTanggal = resources.getStringArray(R.array.data_tanggal)
        val dataHari = resources.getStringArray(R.array.data_hari)
        val listTanggal = ArrayList<Tanggal>()
        for (i in dataTanggal.indices){
            val tanggal = Tanggal(dataTanggal[i], dataHari[i])
            listTanggal.add(tanggal)
        }
        return listTanggal
    }

    private fun ShowTanggal(){
        rvTanggal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listTanggalAdapter = ListTanggalAdapter(list)
        rvTanggal.adapter = listTanggalAdapter
    }
}