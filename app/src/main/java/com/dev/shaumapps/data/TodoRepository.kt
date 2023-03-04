package com.dev.shaumapps.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.dev.shaumapps.data.local.entity.Todo
import com.dev.shaumapps.data.local.room.ShaumDatabase
import com.dev.shaumapps.data.local.room.TodoDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TodoRepository(application: Application) {
    private val mTodoDao: TodoDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = ShaumDatabase.getInstance(application)
        mTodoDao = db.todoDao()
    }

    fun getAllTodos(): LiveData<List<Todo>> = mTodoDao.getAllTodos()

    fun insert(todo: Todo) {
        executorService.execute { mTodoDao.insert(todo) }
    }

    fun update(todo: Todo) {
        executorService.execute { mTodoDao.update(todo) }
    }
}