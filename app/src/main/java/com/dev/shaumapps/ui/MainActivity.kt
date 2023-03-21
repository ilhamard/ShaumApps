package com.dev.shaumapps.ui

import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.dev.shaumapps.R
import com.dev.shaumapps.databinding.ActivityMainBinding
import com.dev.shaumapps.util.SessionManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isIkhwan = false
    private var isAkhwat = false
    private lateinit var sessionManager: SessionManager
    private val userGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sessionManager = SessionManager(this)

        binding.cardIkhwan.setOnClickListener {
            binding.cardIkhwan.background = selectedItemColor()
            binding.cardAkhwat.background =
                ColorDrawable(ContextCompat.getColor(this, R.color.secondary))
            isIkhwan = true
            isAkhwat = false
        }

        binding.cardAkhwat.setOnClickListener {
            binding.cardIkhwan.background =
                ColorDrawable(ContextCompat.getColor(this, R.color.secondary))
            binding.cardAkhwat.background = selectedItemColor()
            isIkhwan = false
            isAkhwat = true
        }

        var isObserverTriggered = false
        sessionManager.user_gender.asLiveData().observe(this) { userGender ->
            if (!isObserverTriggered && userGender != null) {
                isObserverTriggered = true
                Intent(this@MainActivity, HomePageActivity::class.java).also {
                    it.putExtra(HomePageActivity.EXTRA_GENDER, userGender)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }

        binding.btnStart.setOnClickListener {
            val intent = Intent(this@MainActivity, HomePageActivity::class.java)
            if (isIkhwan) {
                isObserverTriggered = true
                intent.putExtra(HomePageActivity.EXTRA_GENDER, "ikhwan")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                lifecycleScope.launch {
                    sessionManager.clearUserGender()
                    sessionManager.saveUserGender("ikhwan")
                }
                startActivity(intent)
            } else if (isAkhwat) {
                isObserverTriggered = true
                intent.putExtra(HomePageActivity.EXTRA_GENDER, "akhwat")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                lifecycleScope.launch {
                    sessionManager.clearUserGender()
                    sessionManager.saveUserGender("akhwat")
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Pilih terlebih dahulu jenis kelamin", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun selectedItemColor(): LayerDrawable {

        val radius = resources.getDimension(R.dimen.card_corner_radius)
        val corners = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        val backgroundDrawable = ShapeDrawable(RoundRectShape(corners, null, null)).apply {
            paint.color = ContextCompat.getColor(this@MainActivity, R.color.secondary)
            paint.style = Paint.Style.FILL
        }

        val borderRadius = radius - 2
        val borderCorners = floatArrayOf(
            borderRadius,
            borderRadius,
            borderRadius,
            borderRadius,
            borderRadius,
            borderRadius,
            borderRadius,
            borderRadius
        )
        val borderDrawable = ShapeDrawable(RoundRectShape(borderCorners, null, null)).apply {
            paint.color = ContextCompat.getColor(this@MainActivity, R.color.primary)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 4f
        }

        val layerDrawable = LayerDrawable(arrayOf(backgroundDrawable, borderDrawable))
        layerDrawable.setLayerInset(1, 0, 0, 0, 0)
        return layerDrawable
    }
}