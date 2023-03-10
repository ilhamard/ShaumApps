package com.dev.shaumapps.ui.asmaul_husna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.shaumapps.R
import com.dev.shaumapps.databinding.ActivityAsmaulHusnaBinding
import com.dev.shaumapps.util.AsmaulHusna

class AsmaulHusnaActivity : AppCompatActivity() {

    private lateinit var rvAsmaul: RecyclerView
    private lateinit var binding: ActivityAsmaulHusnaBinding
    private val listAsmaul = ArrayList<AsmaulHusna>()
    private val muncul =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsmaulHusnaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvAsmaul = findViewById(R.id.rv_asmaul_husna)
        rvAsmaul.setHasFixedSize(true)

        listAsmaul.addAll(getAsmaulList())
        showAsmaul()



    }

    private fun getAsmaulList(): ArrayList<AsmaulHusna>{
        val dataAsmaul = resources.getStringArray(R.array.data_asmaulHusna)
        val dataNumber = resources.getStringArray(R.array.data_urutNumber)
        val dataMaknaAsmaul = resources.getStringArray(R.array.data_asmaul_makna)
        val listAsmaul = ArrayList<AsmaulHusna>()
        for (i in dataAsmaul.indices){
            val asmaul = AsmaulHusna(dataAsmaul[i], dataNumber[i], dataMaknaAsmaul[i] )
            listAsmaul.add(asmaul)
        }
        return  listAsmaul
    }

    private fun showAsmaul(){
        rvAsmaul.layoutManager = LinearLayoutManager(this)
        val listAsmaulAdapter = ListAsmaulAdapter(listAsmaul)
        rvAsmaul.adapter = listAsmaulAdapter
    }
}