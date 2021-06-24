package com.example.thorium.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Cell(

    @PrimaryKey(autoGenerate = false) val cid: Int? = null,
    val lac_tac: String,
    val rac: String,
    val plmn: String,
)