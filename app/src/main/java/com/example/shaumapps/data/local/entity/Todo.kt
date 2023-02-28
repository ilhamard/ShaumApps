package com.example.shaumapps.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity
@Parcelize
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo("judul")
    var judul: String? = null,

    @ColumnInfo("deskripsi")
    var deskripsi: String? = null,

    @ColumnInfo("status")
    var status: Boolean? = false,

    @ColumnInfo("created_at")
    var createdAt: String? = null,

    @ColumnInfo("updated_at")
    var updatedAt: String? = null
): Parcelable