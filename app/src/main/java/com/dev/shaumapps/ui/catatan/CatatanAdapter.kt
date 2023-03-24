package com.dev.shaumapps.ui.catatan

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.shaumapps.data.local.entity.CatatanData
import com.dev.shaumapps.databinding.ItemListCatatanBinding
import java.text.SimpleDateFormat
import java.util.*

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
        private val inputFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        private val currentDateTime = Calendar.getInstance()
        private val outputFormatThisWeek = SimpleDateFormat("EEEE, HH:mm", Locale("id", "ID"))
        private val outputFormatOtherWeeks = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        fun bind(catatanData: CatatanData) {
            with(binding) {
                txJudul.text = catatanData.judulCatatan

                val inputDateTime = inputFormat.parse(catatanData.tanggal.toString())
                val outputFormat = if (inputDateTime.after(currentDateTime.apply {
                        add(
                            Calendar.WEEK_OF_YEAR,
                            -1
                        )
                    }.time)) {
                    outputFormatThisWeek
                } else {
                    outputFormatOtherWeeks
                }
                val outputDate = outputFormat.format(inputDateTime)
                txTanggal.text = outputDate

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
