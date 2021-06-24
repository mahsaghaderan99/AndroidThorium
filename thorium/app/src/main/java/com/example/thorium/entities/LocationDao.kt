package com.example.thorium.entities

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.thorium.entities.relations.CellWithLocations

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCell(cell: Cell)

    @Transaction
    @Query("SELECT * FROM cell WHERE cid = :cid")
    suspend fun getCellWithLocations(cid: Int): LiveData<List<CellWithLocations>>

    @Transaction
    @Query("SELECT * FROM cell WHERE cid = :cid")
    suspend fun getCellByCid(cid: Int): LiveData<List<Cell>>

    @Transaction
    @Query("SELECT * FROM location ORDER BY id ASC")
    fun getAllLocations(): LiveData<List<Location>>


}