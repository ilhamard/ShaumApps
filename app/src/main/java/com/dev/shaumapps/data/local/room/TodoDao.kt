package com.dev.shaumapps.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev.shaumapps.data.local.entity.Todo

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Query("DELETE FROM todo WHERE judul = :judul")
    suspend fun deleteTodo(judul: String)

    @Query("SELECT * FROM todo WHERE isDone = false ORDER BY id DESC")
    fun getAllTodo(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE isDone = true")
    fun getTodoChecked(): LiveData<List<Todo>>

    @Query("SELECT EXISTS(SELECT * FROM todo WHERE isDone = :isDone)")
    fun isTodoChecked(isDone: Boolean): LiveData<Boolean>
}