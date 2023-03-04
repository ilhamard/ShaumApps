package com.dev.shaumapps.ui.doa

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.shaumapps.data.remote.response.DoaHarianResponseItem
import com.dev.shaumapps.databinding.ItemListDoaBinding

class ListDoaAdapter : RecyclerView.Adapter<ListDoaAdapter.ListDoaViewHolder>() {
    private val listDoa = ArrayList<DoaHarianResponseItem>()

    fun setListDoa(listDoa: List<DoaHarianResponseItem>) {
        this.listDoa.clear()
        this.listDoa.addAll(listDoa)
        notifyDataSetChanged()
        Log.d("ListDoaAdapter", "cek adapter ${listDoa.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDoaViewHolder {
        val binding = ItemListDoaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListDoaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDoa.size
    }

    override fun onBindViewHolder(holder: ListDoaViewHolder, position: Int) {
        holder.bind(listDoa[position])
    }

    inner class ListDoaViewHolder(private val binding: ItemListDoaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DoaHarianResponseItem) {
            with(binding) {
                tvNomor.text = item.id
                tvJudul.text = item.doa
                itemDoa.setOnClickListener {
                    val intent = Intent(it.context, DetailDoaActivity::class.java)
                    intent.putExtra(DetailDoaActivity.EXTRA_ID_DOA, item.id)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}