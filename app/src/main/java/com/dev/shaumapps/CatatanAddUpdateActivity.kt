package com.dev.shaumapps

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProvider
import com.dev.shaumapps.data.local.entity.CatatanData
import com.dev.shaumapps.databinding.ActivityCatatanAddUpdateBinding
import com.dev.shaumapps.ui.catatan.CatatanViewModel
import com.dev.shaumapps.util.DateHelper
import com.dev.shaumapps.util.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CatatanAddUpdateActivity : AppCompatActivity() {

    private var isEdit = false
    private var catatanData:CatatanData? = null
    private lateinit var catatanViewModel: CatatanViewModel

    private var _binding: ActivityCatatanAddUpdateBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCatatanAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        catatanViewModel = obtainViewModel(this@CatatanAddUpdateActivity)

        catatanData = intent.getParcelableExtra(EXTRA_CATATAN)
        if (catatanData != null){
            isEdit = true
        } else {
            catatanData = CatatanData()
        }

        val actionBarCatatan: String
        val btnCatatan: String

        if (isEdit){
            actionBarCatatan = getString(R.string.change)
            btnCatatan = getString(R.string.update)
            if (catatanData != null){
                catatanData?.let { catatanData ->
                    binding?.edtTitle?.setText(catatanData.judulCatatan)
                    binding?.edtDescription?.setText(catatanData.deskripsi)
                }
            }
        } else {
            actionBarCatatan = getString(R.string.add)
            btnCatatan = getString(R.string.save)
        }

        supportActionBar?.title = actionBarCatatan
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.fabSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val deskripsi = binding?.edtDescription?.text.toString().trim()
            when{
                title.isEmpty() -> {
                    binding?.edtTitle?.error = "Judul Tidak Boleh Kosong"
                }
                deskripsi.isEmpty() ->{
                    binding?.edtDescription?.error = "Deskripsi Tidak Boleh Kosong"
                }
                else -> {
                    catatanData.let { catatanData ->
                        catatanData?.judulCatatan = title
                        catatanData?.deskripsi = deskripsi
                    }
                    if (isEdit){
                        catatanViewModel.updateCatatan(catatanData as CatatanData)
                        showToast("Apakah Ada Yang Di Update")
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

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun coloringIconFab(){
        val csl = ContextCompat.getColorStateList(this, R.color.white)
        val fabSubmit: FloatingActionButton = findViewById(R.id.fab_submit)
        val fabDelete: FloatingActionButton = findViewById(R.id.fab_delete)
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

    private fun showDeleteDialog(catataData: CatatanData){
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder){
            setTitle("Konfirmasi")
            setMessage("Apakah Anda Yakin Untuk Menghapus Catatan Ini? ")
            setCancelable(false)
            setPositiveButton("ya"){_,_ ->
                catatanViewModel.deleteCatatan(catataData)
                finish()
            }
            setNegativeButton("tidak"){ dialog, _ -> dialog.cancel()}
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): CatatanViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(CatatanViewModel::class.java)
    }


    companion object{
        const val EXTRA_CATATAN = "extra_catatan"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}