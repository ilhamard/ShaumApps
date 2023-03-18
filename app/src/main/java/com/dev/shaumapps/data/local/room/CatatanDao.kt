package com.dev.shaumapps.data.local.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev.shaumapps.data.local.entity.CatatanData

@Dao
interface CatatanDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCatatan(catatanData: CatatanData)

    @Update
    fun updateCatatan(catatanData: CatatanData)

    @Query("DELETE FROM catatanHarian WHERE judulCatatan = :judulCatatan")
    fun deleteCatatan(judulCatatan: String)

    @Query("SELECT * FROM catatanHarian WHERE isDone = false ORDER BY id ASC")
    fun getAllCatatan(): LiveData<List<CatatanData>>
}