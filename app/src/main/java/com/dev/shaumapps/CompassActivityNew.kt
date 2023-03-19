package com.dev.shaumapps

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.shaumapps.databinding.ActivityCompassNewBinding

class CompassActivityNew : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityCompassNewBinding
    private var currentDegree = 0f
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompassNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Arah Kiblat"

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        if (sensor != null) {
            sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        } else {
            Toast.makeText(applicationContext, "not support", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bar_jadwal_shalat, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when(item.itemId){
            R.id.action_update_loc -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val degree = Math.round(sensorEvent.values[0])
        val animation = RotateAnimation(
            currentDegree, (-degree).toFloat(), Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        animation.duration = 500
        animation.fillAfter = true
        binding.imgCompassKiblat.animation = animation
        currentDegree = -degree.toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}

//    companion object {
//        private var sensorManager: SensorManager? = null
//        private var sensor: Sensor? = null
//    }
}