package com.example.thorium.repositories

import androidx.lifecycle.LiveData
import com.example.thorium.entities.Cell
import com.example.thorium.entities.Location
import com.example.thorium.entities.LocationDao
import com.example.thorium.entities.relations.CellWithLocations

class LocationRepository(private val locationDao: LocationDao) {

    val allLocations: LiveData<List<Location>> = locationDao.getAllLocations()

    suspend fun getCellByCid(cid: Int): LiveData<List<Cell>>
    {
        return locationDao.getCellByCid(cid)
    }

    suspend fun getCellWithLocations(cid: Int): LiveData<List<CellWithLocations>>
    {
        return locationDao.getCellWithLocations(cid)
    }

    suspend fun addLocation(location: Location)
    {
        locationDao.insertLocation(location)
    }

    suspend fun addCell(cell: Cell)
    {
        locationDao.insertCell(cell)
    }

}