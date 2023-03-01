package com.example.shaumapps.di

import android.content.Context
import com.example.shaumapps.data.DoaRepository
import com.example.shaumapps.data.local.room.ShaumDatabase

object Injection {
    fun provideRepository(context: Context): DoaRepository {
        val database = ShaumDatabase.getInstance(context)
        val dao = database.doaDao()
        return DoaRepository.getInstance(dao)
    }
}