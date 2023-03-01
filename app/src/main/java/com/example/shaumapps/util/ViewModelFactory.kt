package com.example.shaumapps.util

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shaumapps.data.DoaRepository
import com.example.shaumapps.di.Injection
import com.example.shaumapps.ui.doa.BookmarkDoaViewModel
import com.example.shaumapps.ui.doa.DoaViewModel

class ViewModelFactory private constructor(private val doaRepository: DoaRepository):
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkDoaViewModel::class.java)){
            return BookmarkDoaViewModel(doaRepository) as T
        } else if (modelClass.isAssignableFrom(DoaViewModel::class.java)){
            return DoaViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}