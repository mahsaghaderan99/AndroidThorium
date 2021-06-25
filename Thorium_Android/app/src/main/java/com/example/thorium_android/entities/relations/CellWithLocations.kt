package com.example.thorium_android.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.thorium_android.entities.Cell
import com.example.thorium_android.entities.LocData

data class CellWithLocations(

    @Embedded val cell: Cell,
    @Relation(
        parentColumn = "cid",
        entityColumn = "cellId"
    )
    val locData:  List<LocData>
) {
}