package com.dev.shaumapps.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.shaumapps.di.Injection
import com.dev.shaumapps.ui.doa.BookmarkDoaViewModel
import com.dev.shaumapps.ui.doa.DoaViewModel

class ViewModelFactory private constructor(private val doaRepository: com.dev.shaumapps.data.DoaRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkDoaViewModel::class.java)) {
            return BookmarkDoaViewModel(doaRepository) as T
        } else if (modelClass.isAssignableFrom(DoaViewModel::class.java)) {
            return DoaViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}