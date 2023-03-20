package com.dev.shaumapps.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "catatanHarian")
@Parcelize
data class CatatanData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "judulCatatan")
    var judulCatatan: String? = null,

    @ColumnInfo(name = "tanggal")
    var tanggal: String? = null,

    @ColumnInfo(name = "deskripsi")
    var deskripsi: String? = null,
) : Parcelable
