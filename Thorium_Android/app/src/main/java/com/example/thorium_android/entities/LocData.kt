package com.example.thorium_android.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class LocData(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,

    val latitude: Double,
    val longitude:Double,
    val time: Long,
    val cellId: String
)
{}