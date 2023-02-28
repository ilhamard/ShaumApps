package com.example.shaumapps.data

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import com.example.shaumapps.data.local.entity.Todo
import com.example.shaumapps.data.local.room.ShaumRoomDatabase
import com.example.shaumapps.data.local.room.TodoDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ShaumRepository(application: Application) {
    private val mTodoDao: TodoDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = ShaumRoomDatabase.getDatabase(application)
        mTodoDao = db.todoDao()
    }

    fun getAllTodos(): LiveData<List<Todo>> = mTodoDao.getAllTodos()

    fun insert(todo: Todo){
        executorService.execute { mTodoDao.insert(todo) }
    }

    fun update(todo: Todo){
        executorService.execute { mTodoDao.update(todo) }
    }
}