package com.example.thorium_android.entities

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.thorium_android.entities.relations.CellWithLocations

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locData: LocData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCell(cell: Cell)

    @Transaction
    @Query("SELECT * FROM Cell WHERE cid = :cid")
    fun getCellWithLocations(cid: String): LiveData<List<CellWithLocations>>

    @Transaction
    @Query("SELECT * FROM Cell")
    fun getAllCellWithLocations(): LiveData<List<CellWithLocations>>

    @Transaction
    @Query("SELECT * FROM Cell WHERE cid = :cid")
    fun getCellByCid(cid: String): LiveData<List<Cell>>

    @Transaction
    @Query("SELECT * FROM Cell ORDER BY cid ASC")
    fun getAllCells(): LiveData<List<Cell>>

    @Transaction
    @Query("SELECT * FROM LocData ORDER BY id ASC")
    fun getAllLocations(): LiveData<List<LocData>>


}