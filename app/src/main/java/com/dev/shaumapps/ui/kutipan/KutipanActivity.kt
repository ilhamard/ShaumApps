package com.dev.shaumapps.ui.kutipan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.shaumapps.R
import com.dev.shaumapps.databinding.ActivityKutipanBinding

class KutipanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKutipanBinding
    private lateinit var kutipanList: ArrayList<Kutipan>
    private lateinit var kutipanAdapter: KutipanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKutipanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Kutipan"

        init()
    }

    private fun init() {
        binding.recyclerKutipan.setHasFixedSize(true)
        binding.recyclerKutipan.layoutManager = GridLayoutManager(this, 2)

        kutipanList = ArrayList()
        imgList()

        kutipanAdapter = KutipanAdapter(kutipanList)
        binding.recyclerKutipan.adapter = kutipanAdapter

        kutipanAdapter.setOnItemClickCallBack(object : KutipanAdapter.OnItemClickCallBack {
            override fun onItemClickcallBack(data: Kutipan) {
                showSelectedImageKutipan(data)
            }
        })
    }

    private fun imgList() {
        kutipanList.add(Kutipan(R.drawable.imgkutipan1))
        kutipanList.add(Kutipan(R.drawable.imgkutipan2))
        kutipanList.add(Kutipan(R.drawable.imgkutipan3))
        kutipanList.add(Kutipan(R.drawable.imgkutipan4))
        kutipanList.add(Kutipan(R.drawable.imgkutipan5))
        kutipanList.add(Kutipan(R.drawable.imgkutipan6))
        kutipanList.add(Kutipan(R.drawable.imgkutipan7))
        kutipanList.add(Kutipan(R.drawable.imgkutipan8))
        kutipanList.add(Kutipan(R.drawable.imgkutipan9))
        kutipanList.add(Kutipan(R.drawable.imgkutipan10))
        kutipanList.add(Kutipan(R.drawable.imgkutipan11))
        kutipanList.add(Kutipan(R.drawable.imgkutipan12))
        kutipanList.add(Kutipan(R.drawable.imgkutipan13))
        kutipanList.add(Kutipan(R.drawable.imgkutipan14))
        kutipanList.add(Kutipan(R.drawable.imgkutipan15))
        kutipanList.add(Kutipan(R.drawable.imgkutipan16))
        kutipanList.add(Kutipan(R.drawable.imgkutipan17))
        kutipanList.add(Kutipan(R.drawable.imgkutipan18))
        kutipanList.add(Kutipan(R.drawable.imgkutipan19))
        kutipanList.add(Kutipan(R.drawable.imgkutipan20))
        kutipanList.add(Kutipan(R.drawable.imgkutipan21))
        kutipanList.add(Kutipan(R.drawable.imgkutipan22))
        kutipanList.add(Kutipan(R.drawable.imgkutipan23))
        kutipanList.add(Kutipan(R.drawable.imgkutipan24))
        kutipanList.add(Kutipan(R.drawable.imgkutipan25))
        kutipanList.add(Kutipan(R.drawable.imgkutipan26))
        kutipanList.add(Kutipan(R.drawable.imgkutipan27))
        kutipanList.add(Kutipan(R.drawable.imgkutipan28))
        kutipanList.add(Kutipan(R.drawable.imgkutipan29))

    }

    private fun showSelectedImageKutipan(kutpan: Kutipan) {
        val intent = Intent(this@KutipanActivity, DetailKutipanActivity::class.java)
        intent.putExtra(DetailKutipanActivity.EXTRA_KUTIPAN, kutpan.image.toString())
        startActivity(intent)
    }
}

