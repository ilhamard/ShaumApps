package com.example.shaumapps.ui.doa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shaumapps.data.remote.response.DoaHarianResponseItem
import com.example.shaumapps.data.remote.response.HadisResponse
import com.example.shaumapps.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class DoaViewModel : ViewModel() {

    private val _doaResponse = MutableLiveData<List<DoaHarianResponseItem>>()
    val doaRespone: LiveData<List<DoaHarianResponseItem>> = _doaResponse

    private val _hadisResponse = MutableLiveData<HadisResponse>()
    val hadisRespone: LiveData<HadisResponse> = _hadisResponse

    init {
        getDoaHarian()
    }

    private fun getDoaHarian() {
        val client = ApiConfig.getApiService().getDoaHarian()
        client.enqueue(object : Callback<List<DoaHarianResponseItem>> {
            override fun onResponse(
                call: Call<List<DoaHarianResponseItem>>,
                response: Response<List<DoaHarianResponseItem>>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                    _doaResponse.value = responseBody
                    }
                } else {
                    Log.e("DoaActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DoaHarianResponseItem>>, t: Throwable) {
                Log.e("DoaActivity", "onFailure: ${t.message}")
            }
        })
    }

    fun getDoaByJudul(judul: String){
        val client = ApiConfig.getApiService().getDoaByJudul(judul)
        client.enqueue(object : Callback<DoaHarianResponseItem>{
            override fun onResponse(
                call: Call<DoaHarianResponseItem>,
                response: Response<DoaHarianResponseItem>,
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _doaResponse.value = arrayListOf(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<DoaHarianResponseItem>, t: Throwable) {
                Log.e("DoaViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getHadis() {
        val hrList = mutableListOf("abu-daud","ahmad","bukhari","darimi","ibnu-majah","malik","muslim","nasai","tirmidzi")
        val hrRandom = hrList.random()
        var angka = 1

        when(hrRandom){
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

        val client = ApiConfig.getApiService().getRandomHadis("https://api.hadith.gading.dev/books/$hrRandom/$angka")
        client.enqueue(object : Callback<HadisResponse> {
            override fun onResponse(
                call: Call<HadisResponse>,
                response: Response<HadisResponse>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _hadisResponse.value = responseBody
                    }
                } else {
                    Log.e("DoaActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<HadisResponse>, t: Throwable) {
                Log.e("DoaActivity", "onFailure: ${t.message}")
            }
        })
    }
}