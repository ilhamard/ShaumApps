package com.dev.dzikirkita.ui.catatan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProvider
import com.dev.dzikirkita.R
import com.dev.dzikirkita.data.local.entity.CatatanData
import com.dev.dzikirkita.databinding.ActivityCatatanAddUpdateBinding
import com.dev.dzikirkita.util.DateHelper
import com.dev.dzikirkita.util.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CatatanAddUpdateActivity : AppCompatActivity() {
    private var isEdit = false
    private var catatanData: CatatanData? = null
    private lateinit var catatanViewModel: CatatanViewModel
    private lateinit var binding: ActivityCatatanAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatatanAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        catatanViewModel = obtainViewModel(this@CatatanAddUpdateActivity)

        catatanData = intent.getParcelableExtra(EXTRA_CATATAN)
        if (catatanData != null) {
            isEdit = true
        } else {
            catatanData = CatatanData()
        }

        val actionBarCatatan: String

        if (isEdit) {
            actionBarCatatan = getString(R.string.change)
            if (catatanData != null) {
                binding.fabDeleteCatatan.visibility = View.VISIBLE
                catatanData?.let { catatanData ->
                    binding.edtTitle.setText(catatanData.judulCatatan)
                    binding.edtDescription.setText(catatanData.deskripsi)
                }
                binding.fabDeleteCatatan.setOnClickListener {
                    showDeleteDialog(catatanData as CatatanData)
                }
            }
        } else {
            actionBarCatatan = getString(R.string.add)
        }

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = actionBarCatatan

        binding.fabSubmitCatatan.setOnClickListener {
            val title = binding.edtTitle.text.toString().trim()
            val deskripsi = binding.edtDescription.text.toString().trim()
            when {
                title.isEmpty() -> {
                    binding.edtTitle.error = "Judul Tidak Boleh Kosong"
                }
                deskripsi.isEmpty() -> {
                    binding.edtDescription.error = "Deskripsi Tidak Boleh Kosong"
                }
                else -> {
                    catatanData.let { catatanData ->
                        catatanData?.judulCatatan = title
                        catatanData?.deskripsi = deskripsi
                    }
                    if (isEdit) {
                        catatanViewModel.updateCatatan(catatanData as CatatanData)
                        showToast("Data berhasil diubah")
                    } else {
                        catatanData?.let { catatanData ->
                            catatanData.tanggal = DateHelper.getCurrentDate()
                        }
                        catatanViewModel.insertCatatan(catatanData as CatatanData)
                        showToast("Data Berhasil di Tambahkan")
                    }
                    finish()
                }
            }
        }
        coloringIconFab()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun coloringIconFab() {
        val csl = ContextCompat.getColorStateList(this, R.color.white)
        val fabSubmit: FloatingActionButton = findViewById(R.id.fab_submit_catatan)
        val fabDelete: FloatingActionButton = findViewById(R.id.fab_delete_catatan)
        val drawableSubmit = fabSubmit.drawable?.mutate()
        val drawableDelete = fabDelete.drawable?.mutate()
        drawableSubmit?.let {
            DrawableCompat.setTintList(it, csl)
            fabSubmit.setImageDrawable(it)
        }
        drawableDelete?.let {
            DrawableCompat.setTintList(it, csl)
            fabDelete.setImageDrawable(it)
        }
    }

    private fun showDeleteDialog(catataData: CatatanData) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle("Konfirmasi")
            setMessage("Apakah Anda Yakin Untuk Menghapus Catatan Ini? ")
            setCancelable(false)
            setPositiveButton("ya") { _, _ ->
                catatanViewModel.deleteCatatan(catataData)
                finish()
            }
            setNegativeButton("tidak") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): CatatanViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(CatatanViewModel::class.java)
    }

    companion object {
        const val EXTRA_CATATAN = "extra_catatan"
    }
}