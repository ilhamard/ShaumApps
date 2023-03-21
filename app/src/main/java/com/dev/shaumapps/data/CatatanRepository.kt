package com.dev.shaumapps.data

import androidx.lifecycle.LiveData
import com.dev.shaumapps.data.local.entity.CatatanData
import com.dev.shaumapps.data.local.room.CatatanDao

class CatatanRepository(private var catatanDao: CatatanDao) {
    fun getAllCatatan(): LiveData<List<CatatanData>> = catatanDao.getAllCatatan()

    suspend fun saveCatatan(catatanData: CatatanData) {
        catatanDao.saveCatatan(catatanData)
    }

    suspend fun updateCatatan(catatanData: CatatanData) {
        catatanDao.updateCatatan(catatanData)
    }

    suspend fun deleteCatatan(judulCatatan: String) {
        catatanDao.deleteCatatan(judulCatatan)
    }

    companion object {
        @Volatile
        private var instance: CatatanRepository? = null
        fun getInstance(
            catatanDao: CatatanDao,
        ): CatatanRepository =
            instance ?: synchronized(this) {
                instance
                    ?: CatatanRepository(catatanDao)
            }.also { instance = it }
    }
}