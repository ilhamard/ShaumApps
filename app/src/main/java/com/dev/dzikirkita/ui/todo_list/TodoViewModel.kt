package com.dev.dzikirkita.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.dzikirkita.data.TodoRepository
import com.dev.dzikirkita.data.local.entity.Todo
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