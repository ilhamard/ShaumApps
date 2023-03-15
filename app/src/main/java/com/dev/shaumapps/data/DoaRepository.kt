package com.dev.shaumapps.data

import androidx.lifecycle.LiveData
import com.dev.shaumapps.data.local.entity.DoaHarian
import com.dev.shaumapps.data.local.room.DoaDao

class DoaRepository(private val doaDao: DoaDao) {

    fun getBookmarkedDoa(): LiveData<List<DoaHarian>> = doaDao.getBookmarkedDoa()

    suspend fun saveDoa(doaHarian: DoaHarian) {
        doaDao.saveDoa(doaHarian)
    }

    suspend fun deleteDoa(judul: String) {
        doaDao.deleteDoa(judul)
    }

    fun isDoaBookmarked(judul: String): LiveData<Boolean> {
        return doaDao.isDoaBookmarked(judul)
    }

    companion object {
        @Volatile
        private var instance: com.dev.shaumapps.data.DoaRepository? = null
        fun getInstance(
            doaDao: DoaDao,
        ): DoaRepository =
            instance ?: synchronized(this) {
                instance
                    ?: DoaRepository(doaDao)
            }.also { instance = it }
    }
}