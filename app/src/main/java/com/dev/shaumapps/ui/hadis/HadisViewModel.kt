package com.dev.shaumapps.ui.hadis

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.shaumapps.data.remote.response.HadisResponse
import com.dev.shaumapps.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class HadisViewModel : ViewModel() {

    private val _hadisResponse = MutableLiveData<HadisResponse>()
    val hadisRespone: LiveData<HadisResponse> = _hadisResponse

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getRandomHadis() {
        val hrList = mutableListOf(
            "abu-daud",
            "ahmad",
            "bukhari",
            "darimi",
            "ibnu-majah",
            "malik",
            "muslim",
            "nasai",
            "tirmidzi"
        )
        val hrRandom = hrList.random()
        var angka = 1

        when (hrRandom) {
            "abu-daud" -> angka = Random.nextInt(4418) + 1
            "ahmad" -> angka = Random.nextInt(12) + 1
            "bukhari" -> angka = Random.nextInt(6638) + 1
            "darimi" -> angka = Random.nextInt(2949) + 1
            "ibnu-majah" -> angka = Random.nextInt(4285) + 1
            "malik" -> angka = Random.nextInt(1587) + 1
            "muslim" -> angka = Random.nextInt(4930) + 1
            "nasai" -> angka = Random.nextInt(5364) + 1
            "tirmidzi" -> angka = Random.nextInt(3625) + 1
        }

        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .getRandomHadis("https://api.hadith.gading.dev/books/$hrRandom/$angka")
        client.enqueue(object : Callback<HadisResponse> {
            override fun onResponse(
                call: Call<HadisResponse>,
                response: Response<HadisResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.data?.contents != null) {
                        _hadisResponse.value = responseBody
                    } else {
                        _isError.value = "Konten tidak tersedia"
                    }
                } else {
                    Log.e("DoaActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<HadisResponse>, t: Throwable) {
                Log.e("DoaActivity", "onFailure: ${t.message}")
                _isError.value = t.message
            }
        })
    }
}