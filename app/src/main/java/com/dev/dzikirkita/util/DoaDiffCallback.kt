package com.dev.dzikirkita.util

import androidx.recyclerview.widget.DiffUtil
import com.dev.dzikirkita.data.remote.response.DoaHarianResponseItem

class DoaDiffCallback(
    private val mOldDoaList: List<DoaHarianResponseItem>,
    private val mNewDoaList: List<DoaHarianResponseItem>,
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
        return oldEmployee.artinya == newEmployee.artinya
    }
}