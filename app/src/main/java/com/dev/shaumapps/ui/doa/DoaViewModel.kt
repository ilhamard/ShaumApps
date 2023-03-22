package com.dev.shaumapps.ui.doa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.shaumapps.data.remote.response.DoaHarianResponseItem
import com.dev.shaumapps.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoaViewModel : ViewModel() {
    private val _doaResponse = MutableLiveData<List<DoaHarianResponseItem>>()
    val doaRespone: LiveData<List<DoaHarianResponseItem>> = _doaResponse
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDoaHarian() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDoaHarian()
        client.enqueue(object : Callback<List<DoaHarianResponseItem>> {
            override fun onResponse(
                call: Call<List<DoaHarianResponseItem>>,
                response: Response<List<DoaHarianResponseItem>>,
            ) {
                _isLoading.value = false
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

    fun getDoaByJudul(judul: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDoaByJudul(judul)
        client.enqueue(object : Callback<DoaHarianResponseItem> {
            override fun onResponse(
                call: Call<DoaHarianResponseItem>,
                response: Response<DoaHarianResponseItem>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _doaResponse.value = arrayListOf(responseBody)
                    }
                } else {
                    _errorMessage.value = "Response error : $response.code()"
                    Log.e("DoaActivity", "onFailure: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DoaHarianResponseItem>, t: Throwable) {
                Log.e("DoaViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getDoaById(id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDoaById(id)
        client.enqueue(object : Callback<List<DoaHarianResponseItem>> {
            override fun onResponse(
                call: Call<List<DoaHarianResponseItem>>,
                response: Response<List<DoaHarianResponseItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _doaResponse.value = responseBody
                    }
                }
            }

            override fun onFailure(call: Call<List<DoaHarianResponseItem>>, t: Throwable) {
                Log.e("DoaViewModel", "onFailure: ${t.message}")
            }
        })
    }
}