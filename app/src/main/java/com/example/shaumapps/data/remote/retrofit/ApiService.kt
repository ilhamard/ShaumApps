package com.example.shaumapps.data.remote.retrofit

import com.example.shaumapps.data.remote.response.DoaHarianResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/api")
    fun getDoaHarian(): Call<DoaHarianResponse>
}