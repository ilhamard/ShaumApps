package dev.nocturnbinary.dzikirkita.features.qibla

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nocturnbinary.dzikirkita.di.AccelerometerSensor
import dev.nocturnbinary.dzikirkita.di.MagnetometerSensor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QiblaCompassViewModel @Inject constructor(
    private val sensorManager: SensorManager,
    @AccelerometerSensor private val accelerometer: Sensor?,
    @MagnetometerSensor private val magnetometer: Sensor?,
) : ViewModel() {

    private val _azimuth = MutableStateFlow(0f)
    val azimuth: StateFlow<Float> = _azimuth

    private val sensorEventListener = object : SensorEventListener {
        private val gravity = FloatArray(3)
        private val geomagnetic = FloatArray(3)
        private val rotationMatrix = FloatArray(9)
        private val orientation = FloatArray(3)

        override fun onSensorChanged(event: SensorEvent) {
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    gravity[0] = event.values[0]
                    gravity[1] = event.values[1]
                    gravity[2] = event.values[2]
                }

                Sensor.TYPE_MAGNETIC_FIELD -> {
                    geomagnetic[0] = event.values[0]
                    geomagnetic[1] = event.values[1]
                    geomagnetic[2] = event.values[2]
                }
            }

            if (SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)) {
                SensorManager.getOrientation(rotationMatrix, orientation)
                _azimuth.value =
                    Math.toDegrees(orientation[0].toDouble()).toFloat()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    init {
        if (accelerometer != null || magnetometer != null) {
            viewModelScope.launch {
                sensorManager.registerListener(
                    sensorEventListener,
                    accelerometer,
                    SensorManager.SENSOR_DELAY_UI
                )
                sensorManager.registerListener(
                    sensorEventListener,
                    magnetometer,
                    SensorManager.SENSOR_DELAY_UI
                )
            }
        } else {
            throw IllegalStateException("Accelerometer or magnetometer sensor not available.")
        }
    }
}