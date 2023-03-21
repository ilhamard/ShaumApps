package com.dev.shaumapps.data

import androidx.lifecycle.LiveData
import com.dev.shaumapps.data.local.entity.Todo
import com.dev.shaumapps.data.local.room.TodoDao

class TodoRepository(private val todoDao: TodoDao) {
    fun getAllTodos(): LiveData<List<Todo>> = todoDao.getAllTodo()

    suspend fun saveTodo(todo: Todo) {
        todoDao.saveTodo(todo)
    }

    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(judul: String) {
        todoDao.deleteTodo(judul)
    }

    fun getTodoChecked(): LiveData<List<Todo>> = todoDao.getTodoChecked()

    suspend fun onTodoCheckedChanged(todo: Todo, isChecked: Boolean) =
        todoDao.updateTodo(todo.copy(isDone = isChecked))

    companion object {
        @Volatile
        private var instance: TodoRepository? = null
        fun getInstance(
            todoDao: TodoDao,
        ): TodoRepository =
            instance ?: synchronized(this) {
                instance
                    ?: TodoRepository(todoDao)
            }.also { instance = it }
    }
}