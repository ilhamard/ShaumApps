package com.dev.shaumapps

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CompassActivityNew : AppCompatActivity(), SensorEventListener {
    var ic_compass: ImageView? = null
    private var currentDegree = 0f
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass_new)
        ic_compass = findViewById(R.id.img_compass_kiblat)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        if (sensor != null) {
            sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        } else {
            Toast.makeText(applicationContext, "not support", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val degree = Math.round(sensorEvent.values[0])
        val animation = RotateAnimation(
            currentDegree, (-degree).toFloat(), Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        animation.duration = 500
        animation.fillAfter = true
        ic_compass!!.animation = animation
        currentDegree = -degree.toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}

//    companion object {
//        private var sensorManager: SensorManager? = null
//        private var sensor: Sensor? = null
//    }
}