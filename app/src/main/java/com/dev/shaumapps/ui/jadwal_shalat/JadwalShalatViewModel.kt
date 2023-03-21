package com.dev.shaumapps.ui.jadwal_shalat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.shaumapps.data.remote.response.JadwalShalatResponse
import com.dev.shaumapps.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class JadwalShalatViewModel : ViewModel() {
    private val _jadwalRespone = MutableLiveData<JadwalShalatResponse>()
    val jadwalRespone: LiveData<JadwalShalatResponse> = _jadwalRespone
    private val _setJadwalRespone = MutableLiveData<JadwalShalatResponse>()
    val setJadwalRespone: LiveData<JadwalShalatResponse> = _setJadwalRespone
    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val currentDate = Date()
    private val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val formattedDate: String = formatter.format(currentDate)

    var isImsakAlert = false
    var isShubuhAlert = false
    var isTerbitAlert = false
    var isDzuhurAlert = false
    var isAsharAlert = false
    var isMaghribAlert = false
    var isIsyaAlert = false

    fun getJadwalShalat() {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .getJadwalByKota("http://api.aladhan.com/v1/timingsByCity/$formattedDate?city=Jakarta&country=Indonesia&method=11")
        client.enqueue(object : Callback<JadwalShalatResponse> {
            override fun onResponse(
                call: Call<JadwalShalatResponse>,
                response: Response<JadwalShalatResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _jadwalRespone.value = responseBody
                    } else {
                        _isError.value = "Konten tidak tersedia"
                    }
                } else {
                    Log.e("HadisViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<JadwalShalatResponse>, t: Throwable) {
                Log.e("HadisViewModel", "onFailure: ${t.message}")
                _isError.value = t.message
            }
        })
    }

    fun setJadwalShalat(tgl: String? = formattedDate, kota: String? = "Jakarta") {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .getJadwalByKota("http://api.aladhan.com/v1/timingsByCity/$tgl?city=$kota&country=Indonesia&method=11")
        client.enqueue(object : Callback<JadwalShalatResponse> {
            override fun onResponse(
                call: Call<JadwalShalatResponse>,
                response: Response<JadwalShalatResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _setJadwalRespone.value = responseBody
                    } else {
                        _isError.value = "Konten tidak tersedia"
                    }
                } else {
                    Log.e("HadisViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<JadwalShalatResponse>, t: Throwable) {
                Log.e("HadisViewModel", "onFailure: ${t.message}")
                _isError.value = t.message
            }
        })
    }

    fun alertSound(shalat: String) {
        when (shalat) {
            "Imsak" -> isImsakAlert = !isImsakAlert
            "Shubuh" -> isShubuhAlert = !isShubuhAlert
            "Terbit" -> isTerbitAlert = !isTerbitAlert
            "Dzuhur" -> isDzuhurAlert = !isDzuhurAlert
            "Ashar" -> isAsharAlert = !isAsharAlert
            "Maghrib" -> isMaghribAlert = !isMaghribAlert
            "Isya" -> isIsyaAlert = !isIsyaAlert
        }
    }
}