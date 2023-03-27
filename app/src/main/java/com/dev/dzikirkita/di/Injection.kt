package com.dev.dzikirkita.di

import android.content.Context
import com.dev.dzikirkita.data.CatatanRepository
import com.dev.dzikirkita.data.DoaRepository
import com.dev.dzikirkita.data.TodoRepository
import com.dev.dzikirkita.data.local.room.ShaumDatabase

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