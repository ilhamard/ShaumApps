package com.dev.dzikirkita.ui.asmaul_husna

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev.dzikirkita.R
import com.dev.dzikirkita.data.local.entity.AsmaulHusna
import com.dev.dzikirkita.databinding.ItemListAsmaulHusnaBinding

class ListAsmaulAdapter(private val listAsmaul: ArrayList<AsmaulHusna>) :
    RecyclerView.Adapter<ListAsmaulAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemListAsmaulHusnaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listAsmaul[position])

    }

    override fun getItemCount(): Int = listAsmaul.size

    class ListViewHolder(val binding: ItemListAsmaulHusnaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AsmaulHusna) {
            val startIndex = item.asmaulHusnaMakna.indexOf('(')
            val endIndex = item.asmaulHusnaMakna.lastIndexOf(')')
            val spannableString = SpannableString(item.asmaulHusnaMakna)
            spannableString.setSpan(
                StyleSpan(Typeface.ITALIC),
                startIndex,
                endIndex + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            with(binding) {
                tvAsmaulHusna.text = item.asmaulHusna
                tvNoAsmaulHusna.text = item.numberAsmaulHuna
                isiAsmaulHusnaMakna.text = spannableString

                cons2.setOnClickListener {
                    item.expanded = !item.expanded
                    if (item.expanded) {
                        btnDropdown.setImageDrawable(
                            ContextCompat.getDrawable(
                                it.context,
                                R.drawable.arrowup
                            )
                        )
                        cvIsiAsmaul.visibility = View.VISIBLE
                        Log.d("ListAsmaulAdapter", "cek 2 : ${item.expanded}")
                    } else {
                        btnDropdown.setImageDrawable(
                            ContextCompat.getDrawable(
                                it.context,
                                R.drawable.arrowdown
                            )
                        )
                        cvIsiAsmaul.visibility = View.GONE
                        Log.d("ListAsmaulAdapter", "cek 3 : ${item.expanded}")
                    }
                }
                cvIsiAsmaul.visibility = if (item.expanded) View.VISIBLE else View.GONE
            }
        }
    }
}