package com.dev.dzikirkita.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev.dzikirkita.data.local.entity.DoaHarian

@Dao
interface DoaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveDoa(doaHarian: DoaHarian)

    @Query("DELETE FROM doa_harian WHERE judul = :judul")
    suspend fun deleteDoa(judul: String)

    @Query("SELECT * FROM doa_harian ORDER BY id ASC")
    fun getBookmarkedDoa(): LiveData<List<DoaHarian>>

    @Query("SELECT EXISTS(SELECT * FROM doa_harian WHERE judul = :judul)")
    fun isDoaBookmarked(judul: String): LiveData<Boolean>
}