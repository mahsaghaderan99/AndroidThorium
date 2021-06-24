package com.example.thorium.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.thorium.entities.Cell
import com.example.thorium.entities.Location

data class CellWithLocations(

    @Embedded val cell: Cell,
    @Relation(
        parentColumn = "cid",
        entityColumn = "id"
    )
    val locations:  List<Location>
) {
}