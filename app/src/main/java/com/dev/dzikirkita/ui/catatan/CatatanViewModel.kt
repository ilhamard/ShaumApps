package com.dev.dzikirkita.ui.catatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.dzikirkita.data.CatatanRepository
import com.dev.dzikirkita.data.local.entity.CatatanData
import kotlinx.coroutines.launch

class CatatanViewModel(private val catatanRepository: CatatanRepository) : ViewModel() {
    private val catatanDataVw = MutableLiveData<CatatanData>()

    fun setCatatanHarian(catatanData: CatatanData) {
        catatanDataVw.value = catatanData
    }

    fun insertCatatan(catatanData: CatatanData) {
        viewModelScope.launch {
            catatanRepository.saveCatatan(catatanData)
        }
    }

    fun updateCatatan(catatanData: CatatanData) {
        viewModelScope.launch {
            catatanRepository.updateCatatan(catatanData)
        }
    }

    fun deleteCatatan(catatanData: CatatanData) {
        viewModelScope.launch {
            catatanRepository.deleteCatatan(catatanData.judulCatatan.toString())
        }
    }

    fun getAllCatatan(): LiveData<List<CatatanData>> = catatanRepository.getAllCatatan()
}