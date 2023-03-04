package com.dev.shaumapps.util

import androidx.recyclerview.widget.DiffUtil
import com.dev.shaumapps.data.local.entity.DoaHarian

class DoaDiffCallback(
    private val mOldDoaList: List<DoaHarian>,
    private val mNewDoaList: List<DoaHarian>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldDoaList.size
    }

    override fun getNewListSize(): Int {
        return mNewDoaList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldDoaList[oldItemPosition].id == mNewDoaList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldDoaList[oldItemPosition]
        val newEmployee = mNewDoaList[newItemPosition]
        return oldEmployee.judul == newEmployee.judul
    }
}