package com.dev.dzikirkita.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todo")
@Parcelize
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo("judul")
    var judul: String? = "",

    @ColumnInfo("deskripsi")
    var deskripsi: String? = null,

    @ColumnInfo("isDone")
    var isDone: Boolean? = false,

    @ColumnInfo("date")
    var date: String? = null,
) : Parcelable
