package com.dev.shaumapps.ui.kutipan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.shaumapps.DetailKutipanActivity
import com.dev.shaumapps.R

class KutipanActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var kutipanList: ArrayList<Kutipan>
    private lateinit var kutipanAdapter: KutipanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kutipan)

        init()

    }

    private fun init() {
        recyclerView = findViewById(R.id.recyclerKutipan)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        kutipanList = ArrayList()
        imgList()

        kutipanAdapter = KutipanAdapter(kutipanList)
        recyclerView.adapter = kutipanAdapter

        kutipanAdapter.setOnItemClickCallBack(object : KutipanAdapter.OnItemClickCallBack {
            override fun onItemClickcallBack(data: Kutipan) {
                showSelectedImageKutipan(data)
            }
        })
    }

    private fun imgList() {
        kutipanList.add(Kutipan(R.drawable.imgkutipan1))
        kutipanList.add(Kutipan(R.drawable.imgkutipan2))
        kutipanList.add(Kutipan(R.drawable.imgkutipan1))
        kutipanList.add(Kutipan(R.drawable.imgkutipan2))
        kutipanList.add(Kutipan(R.drawable.imgkutipan1))
        kutipanList.add(Kutipan(R.drawable.imgkutipan2))
        kutipanList.add(Kutipan(R.drawable.imgkutipan1))
        kutipanList.add(Kutipan(R.drawable.imgkutipan2))
        kutipanList.add(Kutipan(R.drawable.imgkutipan1))
        kutipanList.add(Kutipan(R.drawable.imgkutipan2))
    }

    private fun showSelectedImageKutipan(kutpan: Kutipan) {
        val intent = Intent(this@KutipanActivity, DetailKutipanActivity::class.java)
        intent.putExtra(DetailKutipanActivity.EXTRA_KUTIPAN, kutpan.image.toString())
        startActivity(intent)
    }
}

