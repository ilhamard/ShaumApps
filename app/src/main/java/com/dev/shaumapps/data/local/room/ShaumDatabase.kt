package com.dev.shaumapps.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.shaumapps.data.local.entity.CatatanData
import com.dev.shaumapps.data.local.entity.DoaHarian
import com.dev.shaumapps.data.local.entity.Todo

@Database(entities = [Todo::class, DoaHarian::class, CatatanData::class], version = 1)
abstract class ShaumDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun doaDao(): DoaDao
    abstract fun catatanDao(): CatatanDao

    companion object {
        @Volatile
        private var instance: ShaumDatabase? = null
        fun getInstance(context: Context): ShaumDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ShaumDatabase::class.java, "shaum.db"
                ).build()
            }
    }
}