package com.example.shaumapps.ui.doa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shaumapps.data.remote.response.DoaHarianResponseItem
import com.example.shaumapps.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoaViewModel : ViewModel() {

    private val _doaResponse = MutableLiveData<List<DoaHarianResponseItem>>()
    val doaRespone: LiveData<List<DoaHarianResponseItem>> = _doaResponse

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
}