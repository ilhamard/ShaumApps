package com.dev.shaumapps.ui.asmaul_husna

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev.shaumapps.R
import com.dev.shaumapps.util.AsmaulHusna

class ListAsmaulAdapter(private val listAsmaul: ArrayList<AsmaulHusna>) :
    RecyclerView.Adapter<ListAsmaulAdapter.ListViewHolder>() {
    private var muncul = false

    class ListViewHolder(itemViewAsmaul: View) : RecyclerView.ViewHolder(itemViewAsmaul) {
        val tvAsmaulHusna: TextView = itemViewAsmaul.findViewById(R.id.tv_asmaul_husna)
        val tvNoAsmaulHusna: TextView = itemViewAsmaul.findViewById(R.id.tv_no_asmaul_husna)
        val isiAsmaulHusna: TextView = itemViewAsmaul.findViewById(R.id.isi_asmaulHusnaMakna)
        val expandView: ConstraintLayout = itemViewAsmaul.findViewById(R.id.cons2)
        val isExpand: CardView = itemViewAsmaul.findViewById(R.id.cv_isiAsmaul)
        val btnExpand: ImageButton = itemViewAsmaul.findViewById(R.id.btn_dropdown)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val viewAsmaul: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_asmaul_husna, parent, false)
        return ListViewHolder(viewAsmaul)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (asmauHusna, noAsmaulHusna, asmaulHusnaMakna) = listAsmaul[position]
        holder.tvAsmaulHusna.text = asmauHusna
        holder.tvNoAsmaulHusna.text = noAsmaulHusna
//        holder.isiAsmaulHusna.text = AsmaulHusnaMakna
//        holder.isiAsmaulHusna.text = HtmlCompat.fromHtml(
//            asmaulHusnaMakna,
//            HtmlCompat.FROM_HTML_MODE_LEGACY);
        val startIndex = asmaulHusnaMakna.indexOf('(')
        val endIndex = asmaulHusnaMakna.lastIndexOf(')')

        val spannableString = SpannableString(asmaulHusnaMakna)
        spannableString.setSpan(
            StyleSpan(Typeface.ITALIC),
            startIndex,
            endIndex + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

        )
        holder.isiAsmaulHusna.text = spannableString

        holder.expandView.setOnClickListener {
            if (muncul == true) {
                holder.btnExpand.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.arrowup
                    )
                )
                holder.isExpand.visibility = View.VISIBLE
                muncul = !muncul
            } else {
                holder.btnExpand.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.arrowdown
                    )
                )
                holder.isExpand.visibility = View.GONE
                muncul = !muncul
            }
        }


    }

    override fun getItemCount(): Int = listAsmaul.size

}