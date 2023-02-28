package com.example.shaumapps.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shaumapps.data.local.entity.Todo

@Database(entities = [Todo::class], version = 1)
abstract class ShaumRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: ShaumRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ShaumRoomDatabase {
            if (INSTANCE == null){
                synchronized(ShaumRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    ShaumRoomDatabase::class.java, "shaum_database")
                        .build()
                }
            }
            return INSTANCE as ShaumRoomDatabase
        }
    }
}