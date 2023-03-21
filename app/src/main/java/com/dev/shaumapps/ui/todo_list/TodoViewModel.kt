package com.dev.shaumapps.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.shaumapps.data.TodoRepository
import com.dev.shaumapps.data.local.entity.Todo
import kotlinx.coroutines.launch

class TodoViewModel(private val todoRepository: TodoRepository) :
    ViewModel() {

    fun getAllTodos() = todoRepository.getAllTodos()

    fun getTodoChecked() = todoRepository.getTodoChecked()

    fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.saveTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo.judul.toString())
        }
    }

    fun onTodoCheckedChanged(todo: Todo, isChecked: Boolean) {
        viewModelScope.launch {
            todoRepository.onTodoCheckedChanged(todo, isChecked)
        }
    }
}