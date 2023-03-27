package com.dev.dzikirkita.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.dzikirkita.data.CatatanRepository
import com.dev.dzikirkita.data.DoaRepository
import com.dev.dzikirkita.data.TodoRepository
import com.dev.dzikirkita.di.Injection
import com.dev.dzikirkita.ui.catatan.CatatanViewModel
import com.dev.dzikirkita.ui.doa.BookmarkDoaViewModel
import com.dev.dzikirkita.ui.doa.DoaViewModel
import com.dev.dzikirkita.ui.todo_list.TodoViewModel

class ViewModelFactory private constructor(
    private val doaRepository: DoaRepository,
    private val todoRepository: TodoRepository,
    private val catatanRepository: CatatanRepository,
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkDoaViewModel::class.java)) {
            return BookmarkDoaViewModel(doaRepository) as T
        } else if (modelClass.isAssignableFrom(DoaViewModel::class.java)) {
            return DoaViewModel() as T
        } else if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(todoRepository) as T
        } else if (modelClass.isAssignableFrom(CatatanViewModel::class.java)) {
            return CatatanViewModel(catatanRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideDoaRepository(context),
                    Injection.provideTodoRepository(context),
                    Injection.provideCatatanRepository(context)
                )
            }.also { instance = it }
    }
}