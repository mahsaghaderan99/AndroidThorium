package com.example.thorium.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,

    val lat: Double,
    val long:Double
)