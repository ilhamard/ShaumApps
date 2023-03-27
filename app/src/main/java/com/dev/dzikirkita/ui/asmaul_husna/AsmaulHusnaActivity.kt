package com.dev.dzikirkita.ui.asmaul_husna

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.dzikirkita.R
import com.dev.dzikirkita.data.local.entity.AsmaulHusna
import com.dev.dzikirkita.databinding.ActivityAsmaulHusnaBinding

class AsmaulHusnaActivity : AppCompatActivity() {

    private lateinit var rvAsmaul: RecyclerView
    private lateinit var binding: ActivityAsmaulHusnaBinding
    private val listAsmaul = ArrayList<AsmaulHusna>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsmaulHusnaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Asmaul Husna"

        rvAsmaul = findViewById(R.id.rv_asmaul_husna)
        rvAsmaul.setHasFixedSize(true)

        listAsmaul.addAll(getAsmaulList())
        showAsmaul()
    }

    private fun getAsmaulList(): ArrayList<AsmaulHusna> {
        val dataAsmaul = resources.getStringArray(R.array.data_asmaulHusna)
        val dataNumber = resources.getStringArray(R.array.data_urutNumber)
        val dataMaknaAsmaul = resources.getStringArray(R.array.data_asmaul_makna)
        val listAsmaul = ArrayList<AsmaulHusna>()
        for (i in dataAsmaul.indices) {
            val asmaul = AsmaulHusna(dataAsmaul[i], dataNumber[i], dataMaknaAsmaul[i])
            listAsmaul.add(asmaul)
        }
        return listAsmaul
    }

    private fun showAsmaul() {
        rvAsmaul.layoutManager = LinearLayoutManager(this)
        val listAsmaulAdapter = ListAsmaulAdapter(listAsmaul)
        rvAsmaul.adapter = listAsmaulAdapter
    }
}