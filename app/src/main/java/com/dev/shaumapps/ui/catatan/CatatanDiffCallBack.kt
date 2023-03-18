package com.dev.shaumapps.ui.catatan

import androidx.recyclerview.widget.DiffUtil
import com.dev.shaumapps.data.local.entity.CatatanData

class CatatanDiffCallBack(private val oldCatatanList: List<CatatanData>, private val newCatatanList: List<CatatanData>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldCatatanList.size
    }

    override fun getNewListSize(): Int {
        return newCatatanList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCatatanList[oldItemPosition].id == newCatatanList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCatatan = oldCatatanList[oldItemPosition]
        val newCatatan = newCatatanList[newItemPosition]
        return oldCatatan.judulCatatan == newCatatan.judulCatatan && oldCatatan.deskripsi == newCatatan.deskripsi
    }

}