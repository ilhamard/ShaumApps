package com.dev.shaumapps.ui.beranda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dev.shaumapps.R

class BerandaAdapter(private val sliderList: ArrayList<Int>, private val viewpager2: ViewPager2) :
    RecyclerView.Adapter<BerandaAdapter.BerandaViewHolder>() {

    class BerandaViewHolder(sliderView: View) : RecyclerView.ViewHolder(sliderView) {
        val imageView: ImageView = sliderView.findViewById(R.id.img_slider1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BerandaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_slider_beranda, parent, false)
        return BerandaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BerandaViewHolder, position: Int) {
        holder.imageView.setImageResource(sliderList[position])
        if (position == sliderList.size - 1) {
            viewpager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderList.size
    }

    private val runnable = Runnable {
        sliderList.addAll(sliderList)
        notifyDataSetChanged()
    }
}