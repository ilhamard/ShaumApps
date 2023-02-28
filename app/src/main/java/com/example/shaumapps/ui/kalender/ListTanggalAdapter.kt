package com.example.shaumapps.ui.kalender

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shaumapps.R
import com.example.shaumapps.util.Tanggal

class ListTanggalAdapter(private val listTanggal : ArrayList<Tanggal>) : RecyclerView.Adapter<ListTanggalAdapter.ListViewHolder>() {
    class ListViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvTanggal : TextView = itemView.findViewById(R.id.txt_tanggal)
        val tvHari: TextView = itemView.findViewById(R.id.txt_Hari)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_kalender, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (tanggal, hari) = listTanggal[position]
        holder.tvTanggal.text = tanggal
        holder.tvHari.text = hari
    }

    override fun getItemCount(): Int = listTanggal.size

}