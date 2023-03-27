package com.dev.dzikirkita.ui.doa

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.dzikirkita.databinding.ActivityBookmarkDoaBinding
import com.dev.dzikirkita.util.ViewModelFactory

class BookmarkDoaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkDoaBinding
    private lateinit var viewModel: BookmarkDoaViewModel
    private lateinit var rvAdapter: BookmarkedDoaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkDoaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Doa Tersimpan"

        viewModel = obtainViewModel(this)

        viewModel.getBookmarkedDoa().observe(this) {

            rvAdapter.setListDoa(it)

            if (it.isEmpty()) {
                binding.apply {
                    imgInit.visibility = View.VISIBLE
                    tvInit1.visibility = View.VISIBLE
                    tvInit2.visibility = View.VISIBLE
                    tvInit3.visibility = View.VISIBLE
                }
            }
        }

        setRecyclerView()
    }

    private fun obtainViewModel(activity: AppCompatActivity): BookmarkDoaViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[BookmarkDoaViewModel::class.java]
    }

    private fun setRecyclerView() {
        rvAdapter = BookmarkedDoaAdapter()
        binding.rvBookmarkedDoa.apply {
            layoutManager = LinearLayoutManager(this@BookmarkDoaActivity)
            setHasFixedSize(true)
            adapter = rvAdapter
        }
    }
}