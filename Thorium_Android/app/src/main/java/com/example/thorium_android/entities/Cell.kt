package com.example.thorium_android.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Cell(

    @PrimaryKey(autoGenerate = false) val cid: String,
    val lac_tac: String,
    val rac: String,
    val mcc: String,
    val mnc: String,
    val cellType: String,
)