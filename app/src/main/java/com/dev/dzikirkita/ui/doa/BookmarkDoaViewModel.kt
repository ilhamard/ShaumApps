package com.dev.dzikirkita.ui.doa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.dev.dzikirkita.data.DoaRepository
import com.dev.dzikirkita.data.local.entity.DoaHarian
import kotlinx.coroutines.launch

class BookmarkDoaViewModel(private val doaRepository: DoaRepository) :
    ViewModel() {
    private val doaData = MutableLiveData<DoaHarian>()

    fun setDoaHarian(doaHarian: DoaHarian) {
        doaData.value = doaHarian
    }

    val bookmarkStatus = doaData.switchMap {
        doaRepository.isDoaBookmarked(it.judul)
    }

    fun changeBookmark(doaHarian: DoaHarian) {
        viewModelScope.launch {
            if (bookmarkStatus.value as Boolean) {
                doaRepository.deleteDoa(doaHarian.judul)
            } else {
                doaRepository.saveDoa(doaHarian)
            }
        }
    }

    fun getBookmarkedDoa() = doaRepository.getBookmarkedDoa()
}