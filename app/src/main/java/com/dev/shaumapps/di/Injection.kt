package com.dev.shaumapps.di

import android.content.Context
import com.dev.shaumapps.data.DoaRepository
import com.dev.shaumapps.data.local.room.ShaumDatabase

object Injection {
    fun provideRepository(context: Context): com.dev.shaumapps.data.DoaRepository {
        val database = ShaumDatabase.getInstance(context)
        val dao = database.doaDao()
        return DoaRepository.getInstance(dao)
    }
}