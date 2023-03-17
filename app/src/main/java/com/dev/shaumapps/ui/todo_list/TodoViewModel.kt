package com.dev.shaumapps.ui.todo_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.shaumapps.data.TodoRepository
import com.dev.shaumapps.data.local.entity.Todo
import kotlinx.coroutines.launch

class TodoViewModel(private val todoRepository: TodoRepository) :
    ViewModel() {

    private val todoData = MutableLiveData<Todo>()

    fun setTodoHarian(todo: Todo) {
        todoData.value = todo
    }

    fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.saveTodo(todo)
        }
    }

    fun onTodoCheckedChanged(todo: Todo, isChecked: Boolean) {
        viewModelScope.launch {
            todoRepository.onTodoCheckedChanged(todo, isChecked)
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

//    val checkedStatus = todoData.switchMap {
//        todoRepository.isTodoChecked(it.judul.toString())
//    }

//    fun changeStatus(todo: Todo) {
//        viewModelScope.launch {
//            if (checkedStatus.value as Boolean) {
//                todoRepository.deleteTodo(todo.judul)
//            } else {
//                todoRepository.saveTodo(todo)
//            }
//        }
//    }

    fun getAllTodos() = todoRepository.getAllTodos()
    fun getTodoChecked() = todoRepository.getTodoChecked()

//    fun isDoaBookmarked(judul: String) {
//        return mDoaRepository.isDoaBookmarked(judul)
//    }
}