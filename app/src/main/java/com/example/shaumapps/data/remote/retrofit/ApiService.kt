package com.example.shaumapps.data.remote.retrofit

import com.example.shaumapps.data.remote.response.DoaHarianResponse
import com.example.shaumapps.data.remote.response.DoaHarianResponseItem
import com.example.shaumapps.data.remote.response.HadisResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {
    @GET("/api")
    fun getDoaHarian(): Call<List<DoaHarianResponseItem>>

    @GET("/api/doa/{judul}")
    fun getDoaByJudul(
        @Path("judul") judul: String
    ): Call<DoaHarianResponseItem>

    @GET("/api/{id}")
    fun getDoaById(
        @Path("id") id: String
    ): Call<List<DoaHarianResponseItem>>

    @GET
    fun getRandomHadis(
        @Url url: String
    ): Call<HadisResponse>
}