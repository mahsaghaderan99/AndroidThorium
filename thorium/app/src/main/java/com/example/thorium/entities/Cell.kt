package com.example.thorium.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "location_table")
class Cell(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
)