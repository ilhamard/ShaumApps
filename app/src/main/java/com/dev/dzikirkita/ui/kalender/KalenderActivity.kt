package com.dev.dzikirkita.ui.kalender

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.dzikirkita.R
import com.dev.dzikirkita.data.local.entity.Tanggal
import com.dev.dzikirkita.data.local.entity.TaskHarian
import com.dev.dzikirkita.databinding.ActivityKalenderBinding

class KalenderActivity : AppCompatActivity() {
    private lateinit var rvTanggal: RecyclerView
    private lateinit var rvTask: RecyclerView
    private lateinit var binding: ActivityKalenderBinding
    private val list = ArrayList<Tanggal>()
    private val listTask = ArrayList<TaskHarian>()
    private val Checklist = ArrayList<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvTanggal = findViewById(R.id.rv_tanggal)
        rvTanggal.setHasFixedSize(true)

        list.addAll(getTanggalList())
        ShowTanggal()

        rvTask = findViewById(R.id.rv_task)
        rvTask.setHasFixedSize(true)

        listTask.addAll(getTaskList())
        showTask()

        PushChexbox()

    }

    private fun getTaskList(): ArrayList<TaskHarian> {
        val dataTask = resources.getStringArray(R.array.data_task)
        val listTask = ArrayList<TaskHarian>()
        for (i in dataTask.indices) {
            val task = TaskHarian(dataTask[i])
            listTask.add(task)
        }
        return listTask
    }

    private fun showTask() {
        rvTask.layoutManager = LinearLayoutManager(this)
        val listTaskAdapter = ListTaskAdapter(listTask)
        rvTask.adapter = listTaskAdapter
    }

    private fun getTanggalList(): ArrayList<Tanggal> {
        val dataTanggal = resources.getStringArray(R.array.data_tanggal)
        val listTanggal = ArrayList<Tanggal>()
        for (i in dataTanggal.indices) {
            val tanggal = Tanggal(dataTanggal[i])
            listTanggal.add(tanggal)
        }
        return listTanggal
    }

    private fun ShowTanggal() {
        rvTanggal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listTanggalAdapter = ListTanggalAdapter(list)
        rvTanggal.adapter = listTanggalAdapter
    }

    private fun PushChexbox() {
        binding.rvTask.setOnClickListener {
            val x = binding.rvTask.translationX
            val y = binding.rvTask.translationY

            binding.rvTask.animate()
                .translationXBy(0f)
                .translationYBy(1000f)
                .setDuration(500)
                .start()

        }

    }
}
