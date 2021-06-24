package com.example.thorium_android.repositories

import androidx.lifecycle.LiveData
import com.example.thorium_android.entities.Cell
import com.example.thorium_android.entities.LocData
import com.example.thorium_android.entities.LocationDao
import com.example.thorium_android.entities.relations.CellWithLocations

class LocationRepository(private val locationDao: LocationDao) {

    val allLocations: LiveData<List<LocData>> = locationDao.getAllLocations()

    suspend fun getCellByCid(cid: Int): LiveData<List<Cell>>
    {
        return locationDao.getCellByCid(cid)
    }

    suspend fun getCellWithLocations(cid: Int): LiveData<List<CellWithLocations>>
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

}