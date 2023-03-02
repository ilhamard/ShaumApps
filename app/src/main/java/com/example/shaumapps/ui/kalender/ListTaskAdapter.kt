package com.example.shaumapps.ui.kalender

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shaumapps.R
import com.example.shaumapps.util.TaskHarian

class ListTaskAdapter (private val listTask : ArrayList<TaskHarian>) : RecyclerView.Adapter<ListTaskAdapter.ListViewTaskHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewTaskHolder {
        val view: View =LayoutInflater.from(parent.context).inflate(R.layout.item_list_task, parent, false)
        return ListViewTaskHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewTaskHolder, position: Int) {
        val (taskHarian) = listTask[position]
        holder.tvTask.text = taskHarian
        holder.tvTask.setOnClickListener {
            Toast.makeText(holder.itemView.context, "test " + listTask[holder.bindingAdapterPosition].taskPerHari, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = listTask.size

    inner class ListViewTaskHolder (itemTask: View): RecyclerView.ViewHolder(itemTask){
        val tvTask: TextView = itemView.findViewById(R.id.chck_task)
    }

}