package com.dev.dzikirkita.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "doa_harian")
@Parcelize
data class DoaHarian(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "judul")
    val judul: String,

    @ColumnInfo(name = "ayat")
    val ayat: String,

    @ColumnInfo(name = "latin")
    val latin: String,

    @ColumnInfo(name = "artinya")
    val artinya: String,
) : Parcelable