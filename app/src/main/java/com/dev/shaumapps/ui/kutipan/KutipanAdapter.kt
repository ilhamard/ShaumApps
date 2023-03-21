package com.dev.shaumapps.ui.kutipan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dev.shaumapps.R

class KutipanAdapter(private val kutipanList: ArrayList<Kutipan>) :
    RecyclerView.Adapter<KutipanAdapter.KutipanViewHolder>() {
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KutipanViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_kutipan, parent, false)
        return KutipanViewHolder(view)
    }

    override fun getItemCount(): Int {
        return kutipanList.size
    }

    override fun onBindViewHolder(holder: KutipanViewHolder, position: Int) {
        val kutipan = kutipanList[position]
        holder.imageView.setImageResource(kutipan.image)
        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClickcallBack(kutipanList[holder.bindingAdapterPosition])
        }
    }

    class KutipanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_itm_kutipan)
    }

    interface OnItemClickCallBack {
        fun onItemClickcallBack(data: Kutipan)
    }
}