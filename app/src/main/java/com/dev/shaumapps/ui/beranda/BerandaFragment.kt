package com.dev.shaumapps.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.shaumapps.R
import com.dev.shaumapps.databinding.FragmentBerandaBinding
import com.dev.shaumapps.ui.asmaul_husna.AsmaulHusnaActivity
import com.dev.shaumapps.ui.doa.DoaActivity
import com.dev.shaumapps.ui.hadis.HadisActivity
import com.dev.shaumapps.ui.hadis.HadisViewModel
import com.dev.shaumapps.ui.jadwal_shalat.JadwalShalatActivity
import com.dev.shaumapps.ui.jadwal_shalat.JadwalShalatViewModel
import com.dev.shaumapps.ui.kiblat.KiblatActivity
import com.dev.shaumapps.ui.kutipan.KutipanActivity
import com.dev.shaumapps.ui.tasbih.TasbihActivity
import com.google.android.gms.location.*
import okio.IOException
import java.text.SimpleDateFormat
import java.util.*

class BerandaFragment : Fragment() {
    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var kota: String? = null
    private lateinit var viewModelHadis: HadisViewModel
    private lateinit var viewModelShalat: JadwalShalatViewModel
    private lateinit var countDownTimer: CountDownTimer
    private var targetTime: Long = 0
    private val currentTime =
        getTimeInMillis(SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()), 0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gender = arguments?.getString(HomePageActivity.EXTRA_GENDER)

        if (gender != null) {
            if (gender == "ikhwan") {
                binding.imgProfile.setImageResource(R.drawable.ihwan)
                binding.txtUsername.text = "Akhi"
            } else if (gender == "akhwat") {
                binding.imgProfile.setImageResource(R.drawable.akhwat)
                binding.txtUsername.text = "Ukhti"
            }
        }

        viewModelHadis = ViewModelProvider(this)[HadisViewModel::class.java]
        viewModelHadis.getRandomHadis()
        viewModelHadis.hadisRespone.observe(viewLifecycleOwner) { data ->
            binding.apply {
                "${data.data.contents.id} (${data.data.name})".also { txtHighlightHadits.text = it }
            }
        }

        viewModelShalat = ViewModelProvider(this)[JadwalShalatViewModel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getMyLastLocation()

        targetTime = getTimeInMillis("00:00", 0)
        startCountdown()

        viewModelShalat.setJadwalRespone.observe(viewLifecycleOwner) {
            val waktu = it.data.timings

            val imsak = getTimeInMillis(waktu.imsak, 1)
            val shubuh = getTimeInMillis(waktu.fajr, 0)
            Log.d("JadwalShalatActivity", "cek shubuh: $shubuh")
            val terbit = getTimeInMillis(waktu.sunrise, 0)
            val dzuhur = getTimeInMillis(waktu.dhuhr, 0)
            val ashar = getTimeInMillis(waktu.asr, 0)
            val maghrib = getTimeInMillis(waktu.maghrib, 0)
            val isya = getTimeInMillis(waktu.isha, 0)

            when (currentTime) {
                in imsak until shubuh -> {
                    binding.txtWaktuSolat.text = "Shubuh, ${waktu.fajr} WIB"
                    targetTime = shubuh
                }
                in shubuh until terbit -> {
                    binding.txtWaktuSolat.text = "Terbit, ${waktu.sunrise} WIB"
                    targetTime = terbit
                }
                in terbit until dzuhur -> {
                    binding.txtWaktuSolat.text = "Dzuhur, ${waktu.dhuhr} WIB"
                    targetTime = dzuhur
                }
                in dzuhur until ashar -> {
                    binding.txtWaktuSolat.text = "Ashar, ${waktu.asr} WIB"
                    targetTime = ashar
                }
                in ashar until maghrib -> {
                    binding.txtWaktuSolat.text = "Maghrib, ${waktu.maghrib} WIB"
                    targetTime = maghrib
                }
                in maghrib until isya -> {
                    binding.txtWaktuSolat.text = "Isya, ${waktu.isha} WIB"
                    targetTime = isya
                }
                in isya until imsak -> {
                    binding.txtWaktuSolat.text = "Imsak, ${waktu.imsak} WIB"
                    targetTime = imsak
                }
            }

            countDownTimer.cancel()
            startCountdown()
        }

        binding.icDoa.setOnClickListener {
            val intent = Intent(requireContext(), DoaActivity::class.java)
            startActivity(intent)
        }

        binding.icTasbih.setOnClickListener {
            val intent = Intent(requireContext(), TasbihActivity::class.java)
            startActivity(intent)
        }

        binding.icHadits.setOnClickListener {
            startActivity(Intent(requireContext(), HadisActivity::class.java))
        }

        binding.icJadwalSholat.setOnClickListener {
            startActivity(Intent(requireContext(), JadwalShalatActivity::class.java))
        }
        binding.icKutipan.setOnClickListener {
            startActivity(Intent(requireContext(), KutipanActivity::class.java))
        }

        binding.icAsmaulhusna.setOnClickListener {
            startActivity(Intent(requireContext(), AsmaulHusnaActivity::class.java))
        }

        binding.icKiblat.setOnClickListener {
            startActivity(Intent(requireContext(), KiblatActivity::class.java))
        }

        binding.icKitab.setOnClickListener {
            Toast.makeText(context, "Fitur ini masih tahap pengembangan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getMyLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }
                fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location: Location? ->
                    if (location != null) {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            try {
                                geocoder.getFromLocation(
                                    location.latitude,
                                    location.longitude,
                                    1
                                ) {
                                    Log.d(
                                        "JadwalShalatActivity",
                                        "cek lagi : ${it[0].subAdminArea}"
                                    )
                                    kota = it[0].subAdminArea
                                    viewModelShalat.setJadwalShalat(kota = kota)
                                    binding.tvLocation.text = it[0].subAdminArea
                                }
                            } catch (e: IOException) {
                                Log.e("BerandaFragment", "Error getting location", e)
                                Toast.makeText(
                                    context,
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
                                "JadwalShalatActivity",
                                "cek lagi : ${area?.get(0)?.subAdminArea}"
                            )
                            kota = area?.get(0)?.subAdminArea
                            viewModelShalat.setJadwalShalat(kota = kota)
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
                                        Geocoder(requireContext(), Locale.getDefault())
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        try {
                                            if (location != null) {
                                                geocoder.getFromLocation(
                                                    location.latitude,
                                                    location.longitude,
                                                    1
                                                ) {
                                                    Log.d(
                                                        "JadwalShalatActivity",
                                                        "cek lagi : ${it[0].subAdminArea}"
                                                    )
                                                    kota = it[0].subAdminArea
                                                    viewModelShalat.setJadwalShalat(kota = kota)
                                                    binding.tvLocation.text = it[0].subAdminArea
                                                }
                                            }
                                        } catch (e: IOException) {
                                            Log.e("BerandaFragment", "Error getting location", e)
                                            Toast.makeText(
                                                context,
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
                                            "JadwalShalatActivity",
                                            "cek lagi : ${area?.get(0)?.subAdminArea}"
                                        )
                                        kota = area?.get(0)?.subAdminArea
                                        viewModelShalat.setJadwalShalat(kota = kota)
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
                    context,
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
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATOIN
        )
    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
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
                Toast.makeText(context, "Granted", Toast.LENGTH_SHORT).show()
                getMyLastLocation()
            } else {
                Toast.makeText(context, "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun activateLocDialog() {
        val builder =
            AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog).create()
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

    private fun getTimeInMillis(time: String, addDay: Int): Long {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        calendar.time = dateFormat.parse(time) as Date
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.DAY_OF_MONTH, addDay)

        return calendar.timeInMillis
    }

    private fun startCountdown() {
        var timeDifference = targetTime - currentTime
        Log.d("JadwalShalatActivity", "cek targettime : $targetTime")
        Log.d("JadwalShalatActivity", "cek currenttime : $currentTime")
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

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATOIN = 100
        const val TAG = "BerandaFragment"
        const val ARG_GENDER = "gender"

        fun newInstance(gender: String?): BerandaFragment {
            val fragment = BerandaFragment()
            val args = Bundle()
            args.putString(HomePageActivity.EXTRA_GENDER, gender)
            fragment.arguments = args
            return fragment
        }
    }
}