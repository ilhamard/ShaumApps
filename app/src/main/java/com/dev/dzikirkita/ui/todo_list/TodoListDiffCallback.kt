package com.dev.dzikirkita.ui.todo_list

import androidx.recyclerview.widget.DiffUtil
import com.dev.dzikirkita.data.local.entity.Todo

class TodoDiffCallBack(
    private val oldTodoList: List<Todo>,
    private val newTodoList: List<Todo>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldTodoList.size
    }

    override fun getNewListSize(): Int {
        return newTodoList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTodoList[oldItemPosition].id == newTodoList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTodo = oldTodoList[oldItemPosition]
        val newTodo = newTodoList[newItemPosition]
        return oldTodo.id == newTodo.id && oldTodo.deskripsi == newTodo.deskripsi
    }
}