package com.dev.shaumapps.ui.todo_list

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.shaumapps.data.local.entity.Todo
import com.dev.shaumapps.databinding.ItemTodoBinding

class TodoAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<TodoAdapter.ListTodoViewHolder>() {
    private val listTodo = ArrayList<Todo>()

    fun setListTodo(listTodo: List<Todo>) {
        this.listTodo.clear()
        this.listTodo.addAll(listTodo)
        notifyDataSetChanged()
        Log.d("BookmarkedTodoAdapter", "cek adapter ${listTodo.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTodo.size
    }

    override fun onBindViewHolder(holder: ListTodoViewHolder, position: Int) {
        holder.bind(listTodo[position])
    }

    inner class ListTodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                checkbox.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val todo = listTodo[position]
                        listener.onCheckBoxClick(todo, checkbox.isChecked)
                    }
                }
            }
        }

        fun bind(item: Todo) {
            with(binding) {
                tvJudul.text = item.judul
                checkbox.isChecked = item.isDone == true
                root.setOnClickListener {
                    val intent = Intent(it.context, TodoAddUpdateActivity::class.java)
                    intent.putExtra(TodoAddUpdateActivity.EXTRA_TODO, item)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    interface OnItemClickListener {
        //        fun onItemClick(todo: Todo)
        fun onCheckBoxClick(todo: Todo, isChecked: Boolean)
    }
}