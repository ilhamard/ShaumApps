package com.dev.shaumapps.ui.kutipan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private fun init(){
        recyclerView = findViewById(R.id.recyclerKutipan)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        kutipanList = ArrayList()
        imgList()

        kutipanAdapter = KutipanAdapter(kutipanList)
        recyclerView.adapter = kutipanAdapter
    }
    private fun imgList(){
        kutipanList.add(Kutipan(R.drawable.kutipan))
        kutipanList.add(Kutipan(R.drawable.kutipan))
//        kutipanList.add(Kutipan(R.drawable.kutipan))
//        kutipanList.add(Kutipan(R.drawable.kutipan))
//        kutipanList.add(Kutipan(R.drawable.kutipan))
//        kutipanList.add(Kutipan(R.drawable.kutipan))
//        kutipanList.add(Kutipan(R.drawable.kutipan))
//        kutipanList.add(Kutipan(R.id.img_kutipan))
//        kutipanList.add(Kutipan(R.id.img_kutipan))
//        kutipanList.add(Kutipan(R.id.img_kutipan))
//        kutipanList.add(Kutipan(R.id.img_kutipan))
//        kutipanList.add(Kutipan(R.id.img_kutipan))
//        kutipanList.add(Kutipan(R.id.img_kutipan))

    }
}

