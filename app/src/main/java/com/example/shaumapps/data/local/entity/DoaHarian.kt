package com.example.shaumapps.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class DoaHarian (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "judul")
    val judul: String,

    @ColumnInfo(name = "arab")
    val arab: String,

    @ColumnInfo(name = "artinya")
    val artinya: String,

    @ColumnInfo(name = "latin")
    val latin: String
): Parcelable