package com.dev.shaumapps.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dev.shaumapps.data.local.entity.Todo

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("SELECT * FROM todo ORDER BY id ASC")
    fun getAllTodos(): LiveData<List<Todo>>
}