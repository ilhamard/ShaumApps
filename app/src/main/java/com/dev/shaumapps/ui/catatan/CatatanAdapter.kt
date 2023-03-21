package com.dev.shaumapps.ui.catatan

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.shaumapps.data.local.entity.CatatanData
import com.dev.shaumapps.databinding.ItemListCatatanBinding

class CatatanAdapter : RecyclerView.Adapter<CatatanAdapter.CatatanViewHolder>() {
    private val listCatatan = ArrayList<CatatanData>()

    fun setListCatatan(listCatatan: List<CatatanData>) {
        this.listCatatan.clear()
        this.listCatatan.addAll(listCatatan)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanViewHolder {
        val binding =
            ItemListCatatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatatanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatatanViewHolder, position: Int) {
        holder.bind(listCatatan[position])
    }

    override fun getItemCount(): Int {
        return listCatatan.size
    }

    class CatatanViewHolder(private val binding: ItemListCatatanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(catatanData: CatatanData) {
            with(binding) {
                txJudul.text = catatanData.judulCatatan
                txTanggal.text = catatanData.tanggal
                txDekripsi.text = catatanData.deskripsi
                cvDaftarList.setOnClickListener {
                    val intent = Intent(it.context, CatatanAddUpdateActivity::class.java)
                    intent.putExtra(CatatanAddUpdateActivity.EXTRA_CATATAN, catatanData)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}
