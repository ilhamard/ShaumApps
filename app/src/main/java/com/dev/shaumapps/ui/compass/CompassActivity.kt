package com.dev.shaumapps.ui.compass

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.shaumapps.R
import com.dev.shaumapps.databinding.ActivityCompassBinding

class CompassActivity : AppCompatActivity(), LocationListener {
    private lateinit var binding: ActivityCompassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompassBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_compass)
        setLocation()

    }

    private fun setLocation() {

    }

    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onStatusChanged(provider, status, extras)",
            "android.location.LocationListener"
        )
    )
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
    }


}

