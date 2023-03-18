package com.dev.shaumapps.di

import android.content.Context
import com.dev.shaumapps.data.CatatanRepository
import com.dev.shaumapps.data.DoaRepository
import com.dev.shaumapps.data.TodoRepository
import com.dev.shaumapps.data.local.room.ShaumDatabase

object Injection {
    fun provideDoaRepository(context: Context): DoaRepository {
        val database = ShaumDatabase.getInstance(context)
        val dao = database.doaDao()
        return DoaRepository.getInstance(dao)
    }

    fun provideTodoRepository(context: Context): TodoRepository {
        val database = ShaumDatabase.getInstance(context)
        val dao = database.todoDao()
        return TodoRepository.getInstance(dao)
    }

    fun provideCatatanRepository(context: Context): CatatanRepository {
        val database = ShaumDatabase.getInstance(context)
        val dao = database.catatanDao()
        return CatatanRepository.getInstance(dao)
    }
}