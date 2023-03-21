package com.dev.shaumapps.ui.jadwal_shalat

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.dev.shaumapps.R
import com.dev.shaumapps.databinding.ActivityJadwalShalatBinding
import com.google.android.gms.location.*
import okio.IOException
import java.text.SimpleDateFormat
import java.util.*

class JadwalShalatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJadwalShalatBinding
    private lateinit var viewModel: JadwalShalatViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var countDownTimer: CountDownTimer
    private var targetTime: Long = 0
    private val formatterDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val currentTime =
        getTimeInMillis(SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()), 0)
    private var tgl: String? = null
    private var kota: String? = null
    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = com.dev.shaumapps.databinding.ActivityJadwalShalatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Jadwal Shalat"

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()

        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                spLoaded = true
            } else {
                Toast.makeText(this@JadwalShalatActivity, "Gagal load", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        soundId = sp.load(this@JadwalShalatActivity, R.raw.beduk, 1)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getMyLastLocation()

        viewModel = ViewModelProvider(this)[JadwalShalatViewModel::class.java]

        viewModel.setJadwalShalat()
        viewModel.getJadwalShalat()

        val calendar = Calendar.getInstance()

        val currentDate = Date()
        val formattedDate: String = formatterDate.format(currentDate)
        tgl = formattedDate

        binding.ivForward.setOnClickListener {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            val tomorrowDate = calendar.time
            tgl = formatterDate.format(tomorrowDate)

            viewModel.setJadwalShalat(tgl = tgl, kota = kota)
        }

        binding.ivBackto.setOnClickListener {
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            val yesterdayDate = calendar.time
            tgl = formatterDate.format(yesterdayDate)

            viewModel.setJadwalShalat(tgl = tgl, kota = kota)
        }

        targetTime = getTimeInMillis("00:00", 0)
        startCountdown()

        viewModel.jadwalRespone.observe(this) {
            val waktu = it.data.timings
            val tglHijr = it.data.date.hijri.day
            val bulanHijr = it.data.date.hijri.month.en
            val tahunHijr = it.data.date.hijri.year

            binding.apply {
                timeSubuh.text = waktu.fajr
                timeTerbit.text = waktu.sunrise
                timeDzuhur.text = waktu.dhuhr
                timeAshar.text = waktu.asr
                timeMaghrib.text = waktu.maghrib
                timeIsya.text = waktu.isha
                timeImsak.text = waktu.imsak

                tvTanggal.text = it.data.date.readable
                tvTanggalHijr.text = "$tglHijr $bulanHijr $tahunHijr"
            }

            val imsak = getTimeInMillis(waktu.imsak, 1)
            val shubuh = getTimeInMillis(waktu.fajr, 0)
            Log.d(TAG, "cek shubuh: $shubuh")
            val terbit = getTimeInMillis(waktu.sunrise, 0)
            val dzuhur = getTimeInMillis(waktu.dhuhr, 0)
            val ashar = getTimeInMillis(waktu.asr, 0)
            val maghrib = getTimeInMillis(waktu.maghrib, 0)
            val isya = getTimeInMillis(waktu.isha, 0)

            when (currentTime) {
                in imsak until shubuh -> {
                    binding.tvDate.text = "Shubuh, ${waktu.fajr} WIB"
                    targetTime = shubuh
                }
                in shubuh until terbit -> {
                    binding.tvDate.text = "Terbit, ${waktu.sunrise} WIB"
                    targetTime = terbit
                }
                in terbit until dzuhur -> {
                    binding.tvDate.text = "Dzuhur, ${waktu.dhuhr} WIB"
                    targetTime = dzuhur
                }
                in dzuhur until ashar -> {
                    binding.tvDate.text = "Ashar, ${waktu.asr} WIB"
                    targetTime = ashar
                }
                in ashar until maghrib -> {
                    binding.tvDate.text = "Maghrib, ${waktu.maghrib} WIB"
                    targetTime = maghrib
                }
                in maghrib until isya -> {
                    binding.tvDate.text = "Isya, ${waktu.isha} WIB"
                    targetTime = isya
                }
                in isya until imsak -> {
                    binding.tvDate.text = "Imsak, ${waktu.imsak} WIB"
//                    imsak = getTimeInMillisForImsak(waktu.imsak)
                    targetTime = imsak
                }
            }

            if (currentTime == imsak && viewModel.isImsakAlert || currentTime == shubuh && viewModel.isShubuhAlert
                || currentTime == terbit && viewModel.isTerbitAlert || currentTime == dzuhur && viewModel.isDzuhurAlert
                || currentTime == ashar && viewModel.isAsharAlert || currentTime == maghrib && viewModel.isMaghribAlert
                || currentTime == isya && viewModel.isIsyaAlert
            ) {
                if (spLoaded) {
                    sp.play(soundId, 1f, 1f, 0, 0, 1f)
                }
            }

            countDownTimer.cancel()
            startCountdown()
        }

        viewModel.setJadwalRespone.observe(this) {
            val waktu = it.data.timings
            val tglHijr = it.data.date.hijri.day
            val bulanHijr = it.data.date.hijri.month.en
            val tahunHijr = it.data.date.hijri.year

            binding.apply {
                timeSubuh.text = waktu.fajr
                timeTerbit.text = waktu.sunrise
                timeDzuhur.text = waktu.dhuhr
                timeAshar.text = waktu.asr
                timeMaghrib.text = waktu.maghrib
                timeIsya.text = waktu.isha
                timeImsak.text = waktu.imsak

                tvTanggal.text = it.data.date.readable
                tvTanggalHijr.text = "$tglHijr $bulanHijr $tahunHijr"
            }
        }

        binding.ivSoundImsak.setOnClickListener {
            Toast.makeText(
                this@JadwalShalatActivity,
                "Fitur ini masih tahap pengembangan",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.alertSound("Imsak")
//            alert("Imsak")
        }
        binding.ivSoundShubuh.setOnClickListener {
            Toast.makeText(
                this@JadwalShalatActivity,
                "Fitur ini masih tahap pengembangan",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.alertSound("Shubuh")
//            alert("Shubuh")
        }
        binding.ivSoundTerbit.setOnClickListener {
            Toast.makeText(
                this@JadwalShalatActivity,
                "Fitur ini masih tahap pengembangan",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.alertSound("Terbit")
//            alert("Terbit")
        }
        binding.ivSoundDzuhur.setOnClickListener {
            Toast.makeText(
                this@JadwalShalatActivity,
                "Fitur ini masih tahap pengembangan",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.alertSound("Dzuhur")
//            alert("Dzuhur")
        }
        binding.ivSoundAshar.setOnClickListener {
            Toast.makeText(
                this@JadwalShalatActivity,
                "Fitur ini masih tahap pengembangan",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.alertSound("Ashar")
//            alert("Ashar")
        }
        binding.ivSoundMaghrib.setOnClickListener {
            Toast.makeText(
                this@JadwalShalatActivity,
                "Fitur ini masih tahap pengembangan",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.alertSound("Maghrib")
//            alert("Maghrib")
        }
        binding.ivSoundIsya.setOnClickListener {
            Toast.makeText(
                this@JadwalShalatActivity,
                "Fitur ini masih tahap pengembangan",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.alertSound("Isya")
//            alert("Isya")
        }
    }

//    private fun alert(shalat: String) {
//        when (shalat) {
//            "Imsak" -> {
//                if (viewModel.isImsakAlert) {
//                    binding.ivSoundImsak.setImageResource(R.drawable.icon_on_sound)
//                } else {
//                    binding.ivSoundImsak.setImageResource(R.drawable.icon_off_sound)
//                }
//            }
//            "Shubuh" -> {
//                if (viewModel.isShubuhAlert) {
//                    binding.ivSoundShubuh.setImageResource(R.drawable.icon_on_sound)
//                } else {
//                    binding.ivSoundShubuh.setImageResource(R.drawable.icon_off_sound)
//                }
//            }
//            "Terbit" -> {
//                if (viewModel.isTerbitAlert) {
//                    binding.ivSoundTerbit.setImageResource(R.drawable.icon_on_sound)
//                } else {
//                    binding.ivSoundTerbit.setImageResource(R.drawable.icon_off_sound)
//                }
//            }
//            "Dzuhur" -> {
//                if (viewModel.isDzuhurAlert) {
//                    binding.ivSoundDzuhur.setImageResource(R.drawable.icon_on_sound)
//                } else {
//                    binding.ivSoundDzuhur.setImageResource(R.drawable.icon_off_sound)
//                }
//            }
//            "Ashar" -> {
//                if (viewModel.isAsharAlert) {
//                    binding.ivSoundAshar.setImageResource(R.drawable.icon_on_sound)
//                } else {
//                    binding.ivSoundAshar.setImageResource(R.drawable.icon_off_sound)
//                }
//            }
//            "Maghrib" -> {
//                if (viewModel.isMaghribAlert) {
//                    binding.ivSoundMaghrib.setImageResource(R.drawable.icon_on_sound)
//                } else {
//                    binding.ivSoundMaghrib.setImageResource(R.drawable.icon_off_sound)
//                }
//            }
//            "Isya" -> {
//                if (viewModel.isIsyaAlert) {
//                    binding.ivSoundIsya.setImageResource(R.drawable.icon_on_sound)
//                } else {
//                    binding.ivSoundIsya.setImageResource(R.drawable.icon_off_sound)
//                }
//            }
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bar_jadwal_shalat, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_update_loc -> {
                getMyLastLocation()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }


    private fun startCountdown() {
        var timeDifference = targetTime - currentTime
        Log.d(TAG, "cek targettime : $targetTime")
        Log.d(TAG, "cek currenttime : $currentTime")
        if (timeDifference < 0) {
            timeDifference += 24 * 60 * 60 * 1000
        }

        countDownTimer = object : CountDownTimer(timeDifference, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = millisUntilFinished / (1000 * 60 * 60)
                val minutes = (millisUntilFinished / (1000 * 60)) % 60
                val seconds = (millisUntilFinished / 1000) % 60
                val countDown = "$hours:$minutes:$seconds"

                binding.tvCountdown.text = countDown
            }

            override fun onFinish() {
                binding.tvCountdown.text = "00:00:00"
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    private fun getTimeInMillis(time: String, addDay: Int): Long {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        calendar.time = dateFormat.parse(time) as Date
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.DAY_OF_MONTH, addDay)

        return calendar.timeInMillis
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getMyLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }
                fusedLocationClient.lastLocation.addOnSuccessListener(this) { location: Location? ->
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            try {
                                geocoder.getFromLocation(
                                    location.latitude,
                                    location.longitude,
                                    1
                                ) {
                                    Log.d(
                                        TAG,
                                        "cek lagi : ${it[0].subAdminArea}"
                                    )
                                    kota = it[0].subAdminArea
                                    viewModel.setJadwalShalat(tgl = tgl, kota = kota)
                                    binding.tvLocation.text = it[0].subAdminArea
                                }
                            } catch (e: IOException) {
                                Log.e(TAG, "Error getting location", e)
                                Toast.makeText(
                                    this,
                                    "Gagal mengambil lokasi. Mohon cek koneksi internet anda!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            val area: MutableList<Address>? =
                                geocoder.getFromLocation(
                                    location.latitude,
                                    location.longitude,
                                    1
                                )
                            Log.d(
                                TAG,
                                "cek lagi : ${area?.get(0)?.subAdminArea}"
                            )
                            kota = area?.get(0)?.subAdminArea
                            viewModel.setJadwalShalat(tgl = tgl, kota = kota)
                            binding.tvLocation.text = area?.get(0)?.subAdminArea
                        }
                    } else {
                        val locationRequest = LocationRequest.Builder(5000)
                            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                            .build()

                        fusedLocationClient.requestLocationUpdates(
                            locationRequest,
                            object : LocationCallback() {
                                override fun onLocationResult(p0: LocationResult) {
                                    val location = p0.lastLocation
                                    val geocoder =
                                        Geocoder(this@JadwalShalatActivity, Locale.getDefault())
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        try {
                                            if (location != null) {
                                                geocoder.getFromLocation(
                                                    location.latitude,
                                                    location.longitude,
                                                    1
                                                ) {
                                                    Log.d(
                                                        TAG,
                                                        "cek lagi : ${it[0].subAdminArea}"
                                                    )
                                                    kota = it[0].subAdminArea
                                                    viewModel.setJadwalShalat(
                                                        tgl = tgl,
                                                        kota = kota
                                                    )
                                                    binding.tvLocation.text = it[0].subAdminArea
                                                }
                                            }
                                        } catch (e: IOException) {
                                            Log.e(
                                                TAG,
                                                "Error getting location",
                                                e
                                            )
                                            Toast.makeText(
                                                this@JadwalShalatActivity,
                                                "Gagal mengambil lokasi. Mohon cek koneksi internet anda!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        val area: MutableList<Address>? =
                                            location?.let {
                                                geocoder.getFromLocation(
                                                    it.latitude,
                                                    it.longitude,
                                                    1
                                                )
                                            }
                                        Log.d(
                                            TAG,
                                            "cek lagi : ${area?.get(0)?.subAdminArea}"
                                        )
                                        kota = area?.get(0)?.subAdminArea
                                        viewModel.setJadwalShalat(tgl = tgl, kota = kota)
                                        binding.tvLocation.text = area?.get(0)?.subAdminArea
                                    }
                                }
                            },
                            null
                        )
                    }
                }
            } else {
                Toast.makeText(
                    this@JadwalShalatActivity,
                    "Silahkan nyalakan layanan lokasi",
                    Toast.LENGTH_SHORT
                ).show()
                activateLocDialog()
            }
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATOIN
        )
    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATOIN) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
                getMyLastLocation()
            } else {
                Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun activateLocDialog() {
        val builder =
            AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val view = layoutInflater.inflate(R.layout.custom_dialog_lokasi, null)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        val btnOk = view.findViewById<Button>(R.id.btn_ok)
        builder.setView(view)
        btnCancel.setOnClickListener {
            builder.dismiss()
        }
        btnOk.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(true)
        builder.show()
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATOIN = 100
        const val TAG = "JadwalShalatActivity"
    }
}