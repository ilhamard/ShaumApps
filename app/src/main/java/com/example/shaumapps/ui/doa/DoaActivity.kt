package com.example.shaumapps.ui.doa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shaumapps.data.remote.response.DoaHarianResponse
import com.example.shaumapps.data.remote.response.DoaHarianResponseItem
import com.example.shaumapps.data.remote.retrofit.ApiConfig
import com.example.shaumapps.databinding.ActivityDoaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        viewModel.doaRespone.observe(this){
            rvAdapter.setListDoa(it)
            Log.d("DoaActivity", "cek search : $it")
        }

        viewModel.getHadis()

        viewModel.hadisRespone.observe(this){
            Log.d("DoaActivity", "cek data kedua $it")
        }

        setRecyclerView()

        binding.edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    val inputed: String = binding.edtSearch.text.toString()
                    Toast.makeText(this@DoaActivity, "Mencari sesuatu yang hilang : $inputed", Toast.LENGTH_SHORT).show()
                    viewModel.getDoaByJudul(inputed)
                    return true
                }
                return false
            }
        })
    }

    private fun setRecyclerView(){
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