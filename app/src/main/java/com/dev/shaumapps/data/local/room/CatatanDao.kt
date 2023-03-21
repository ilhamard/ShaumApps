package com.dev.shaumapps.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev.shaumapps.data.local.entity.CatatanData

@Dao
interface CatatanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCatatan(catatanData: CatatanData)

    @Update
    suspend fun updateCatatan(catatanData: CatatanData)

    @Query("DELETE FROM catatanHarian WHERE judulCatatan = :judulCatatan")
    suspend fun deleteCatatan(judulCatatan: String)

    @Query("SELECT * FROM catatanHarian ORDER BY id DESC")
    fun getAllCatatan(): LiveData<List<CatatanData>>
}