package com.dev.shaumapps.ui.tasbih

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.media.SoundPool
import android.os.*
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dev.shaumapps.R
import com.dev.shaumapps.databinding.ActivityTasbihBinding


class TasbihActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTasbihBinding
    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false
    private lateinit var viewModel: TasbihViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasbihBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tasbih"

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()

        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                spLoaded = true
            } else {
                Toast.makeText(this@TasbihActivity, "Gagal load", Toast.LENGTH_SHORT).show()
            }
        }

        soundId = sp.load(this, R.raw.tick, 1)

        viewModel = ViewModelProvider(this).get(TasbihViewModel::class.java)

        binding.jumlahTarget.text = "/ ${viewModel.target}"
        binding.hitung.text = "${viewModel.count}"
        val progress = (viewModel.count * 100) / viewModel.target.toInt()
        binding.progressBar.setProgressWithAnimation(progress.toFloat(), 200)
        alert()

        binding.progressBar.setOnClickListener {
            if (viewModel.isSoundAlert) {
                if (spLoaded) {
                    sp.play(soundId, 1f, 1f, 0, 0, 1f)
                }
            }
            viewModel.increaseCount()
            Log.d("TasbihActivity", "cek ${viewModel.count}")

            if (viewModel.count > viewModel.target.toInt()) {
                viewModel.resetCount()
            }
            if (viewModel.count == viewModel.target.toInt() && viewModel.isVibrateAlert) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrateAlert()
                }
            }
            binding.hitung.text = viewModel.count.toString()
            val progress = (viewModel.count * 100) / viewModel.target.toInt()
            binding.progressBar.setProgressWithAnimation(progress.toFloat(), 200)
        }

        binding.btnReset.setOnClickListener {
            viewModel.resetCount()
            binding.hitung.text = viewModel.count.toString()
            binding.progressBar.progress = viewModel.count.toFloat()
        }

        binding.vibrate.setOnClickListener {
            viewModel.alertWithVibrate()
            alert()
        }

        binding.sound.setOnClickListener {
            viewModel.alertWithSound()
            alert()
        }
    }

    private fun addTargetDzikirDialog() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val view = layoutInflater.inflate(R.layout.custom_view_layout, null)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        val btnSave = view.findViewById<Button>(R.id.btn_save)
        val edtTarget = view.findViewById<EditText>(R.id.edt_target)
        edtTarget.requestFocus()
        builder.setView(view)
        btnCancel.setOnClickListener {
            builder.dismiss()
        }
        btnSave.setOnClickListener {
            val target = edtTarget.text.toString()
            viewModel.setTargetCount(target)
            binding.jumlahTarget.text = "/ $target"
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bar_tasbih, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_dzikir -> {
            addTargetDzikirDialog()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun alert() {
        val colorActive = ContextCompat.getColor(this, R.color.primary)
        if (viewModel.isVibrateAlert) {
            binding.vibrate.backgroundTintList = ColorStateList.valueOf(colorActive)
        } else {
            binding.vibrate.backgroundTintList = null
        }

        if (viewModel.isSoundAlert) {
            binding.sound.backgroundTintList = ColorStateList.valueOf(colorActive)
        } else {
            binding.sound.backgroundTintList = null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun vibrateAlert() {

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibrateEffect: VibrationEffect =
            VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)

        vibrator.cancel()
        vibrator.vibrate(vibrateEffect)
    }
}