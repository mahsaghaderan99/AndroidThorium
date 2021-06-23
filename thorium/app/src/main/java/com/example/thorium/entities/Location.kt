package com.example.thorium.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "location_table")
class Location(
    @PrimaryKey(autoGenerate = false) val cid: Int? = null,
    @ColumnInfo(name = "lac_tac") val lac_tac: String,
    @ColumnInfo(name = "rac") val rac: String,
    @ColumnInfo(name = "plmn") val plmn: String,
)