package com.example.thorium_android.repositories

import androidx.lifecycle.LiveData
import com.example.thorium_android.entities.Cell
import com.example.thorium_android.entities.LocData
import com.example.thorium_android.entities.LocationDao
import com.example.thorium_android.entities.relations.CellWithLocations
import java.util.*
import kotlin.collections.HashMap

class LocationRepository(private val locationDao: LocationDao) {

    var allLocations: LiveData<List<LocData>> = locationDao.getAllLocations()
    var allCells: LiveData<List<Cell>> = locationDao.getAllCells()
    var allCellWithLocations: LiveData<List<CellWithLocations>> = locationDao.getAllCellWithLocations()

    fun getCellByCid(cid: String): LiveData<List<Cell>>
    {
        return locationDao.getCellByCid(cid)
    }

    fun getCellWithLocations(cid: String): LiveData<List<CellWithLocations>>
    {
        return locationDao.getCellWithLocations(cid)
    }

    suspend fun addLocation(locData: LocData)
    {
        locationDao.insertLocation(locData)
    }

    suspend fun addCell(cell: Cell)
    {
        locationDao.insertCell(cell)
    }

    fun removeCell(cid: String)
    {
        locationDao.removeCellByCid(cid)
    }

}