package com.dev.shaumapps.ui.doa

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dev.shaumapps.R
import com.dev.shaumapps.data.local.entity.DoaHarian
import com.dev.shaumapps.databinding.ActivityDetailDoaBinding
import com.dev.shaumapps.util.ViewModelFactory

class DetailDoaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDoaBinding
    private lateinit var viewModel: DoaViewModel
    private lateinit var bookmarkViewModel: BookmarkDoaViewModel
    private var stateId: Int? = null
    private lateinit var doaHarian: DoaHarian
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDoaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        viewModel = ViewModelProvider(this)[DoaViewModel::class.java]
        bookmarkViewModel = obtainViewModel(this)

        val doaId = intent.getStringExtra(EXTRA_ID_DOA)

        if (doaId != null) {
            viewModel.getDoaById(doaId)
        }

        binding.btnPrev.setOnClickListener {
            if (stateId == null) {
                stateId = doaId?.toInt()?.minus(1)
            } else if (stateId!! > 1) {
                stateId = stateId!! - 1
            }

            Log.d("DetailDoaActivity", "cek prev: $stateId")
            if (stateId != null) {
                viewModel.getDoaById(stateId.toString())
            }
        }

        binding.btnForward.setOnClickListener {
            if (stateId == null) {
                stateId = doaId?.toInt()?.plus(1)
            } else if (stateId!! < 37) {
                stateId = stateId!! + 1
            }

            Log.d("DetailDoaActivity", "cek prev: $stateId")
            if (stateId != null) {
                viewModel.getDoaById(stateId.toString())
            }
        }

        viewModel.doaRespone.observe(this) {
            it.forEach { doa ->
                binding.apply {
                    tvNomor.text = "${doa.id} / 37"
                    tvJudul.text = doa.doa
                    tvArab.text = doa.ayat
                    tvLatin.text = "Latin : ${doa.latin}"
                    tvArti.text = "Artinya : ${doa.artinya}"
                }

                doaHarian = DoaHarian(
                    id = doa.id.toInt(),
                    judul = doa.doa,
                    ayat = doa.ayat,
                    latin = doa.latin,
                    artinya = doa.artinya
                )
            }
            bookmarkViewModel.setDoaHarian(doaHarian)
        }

        viewModel.isLoading.observe(this) {
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar_detail_doa, menu)
        this.menu = menu
        bookmarkViewModel.bookmarkStatus.observe(this) { status ->
            setBookmarkState(status)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_bookmark_doa -> {
            Log.d("DetailDoaActivity", "Coba cek datanya : $doaHarian")
            bookmarkViewModel.changeBookmark(doaHarian)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_add_bookmark_doa)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_fill)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_outline)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): BookmarkDoaViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[BookmarkDoaViewModel::class.java]
    }

    companion object {
        const val EXTRA_ID_DOA = "extra_id_doa"
    }
}