package com.dev.shaumapps.data.remote.retrofit

import com.dev.shaumapps.data.remote.response.DoaHarianResponseItem
import com.dev.shaumapps.data.remote.response.HadisResponse
import com.dev.shaumapps.data.remote.response.JadwalShalatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {
    @GET("/api")
    fun getDoaHarian(): Call<List<DoaHarianResponseItem>>

    @GET("/api/doa/{judul}")
    fun getDoaByJudul(
        @Path("judul") judul: String,
    ): Call<DoaHarianResponseItem>

    @GET("/api/{id}")
    fun getDoaById(
        @Path("id") id: String,
    ): Call<List<DoaHarianResponseItem>>

    @GET
    fun getRandomHadis(
        @Url url: String,
    ): Call<HadisResponse>

    @GET
    fun getJadwalByKota(
        @Url url: String,
    ): Call<JadwalShalatResponse>
}