package com.example.shaumapps.ui.doa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shaumapps.R
import com.example.shaumapps.databinding.ActivityDoaBinding

class DoaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoaBinding
    private lateinit var viewModel: DoaViewModel

    private lateinit var rvAdapter: ListDoaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Doa-doa Harian"

        viewModel = ViewModelProvider(this)[DoaViewModel::class.java]

        viewModel.getDoaHarian()

        viewModel.doaRespone.observe(this) {
            rvAdapter.setListDoa(it)
            Log.d("DoaActivity", "cek search : $it")
        }

        viewModel.getHadis()

        viewModel.isLoading.observe(this){
            if (it == true){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.hadisRespone.observe(this) {
            Log.d("DoaActivity", "cek data kedua $it")
        }

        setRecyclerView()

        binding.edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val inputed: String = binding.edtSearch.text.toString()
                    Log.d("DoaActivity", "hilang ha: $inputed")
                    Toast.makeText(
                        this@DoaActivity,
                        "Mencari sesuatu yang hilang : $inputed",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (inputed.isNotEmpty()) {
                        viewModel.getDoaByJudul(inputed)
                    } else {
                        viewModel.getDoaHarian()
                    }

                    return true
                }
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bar_doa, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_bookmarked_doa -> {
            startActivity(Intent(this@DoaActivity, BookmarkDoaActivity::class.java))
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun setRecyclerView() {
        rvAdapter = ListDoaAdapter()
        binding.rvDoa.apply {
            layoutManager = LinearLayoutManager(this@DoaActivity)
            setHasFixedSize(true)
            adapter = rvAdapter
        }
    }
}

//HR
//abu-daud = 4418
//ahmad = 12
//bukhari = 6638
//darimi = 2949
//ibnu-majah = 4285
//malik = 1587
//muslim = 4930
//nasai = 5364
//tirmidzi = 3625