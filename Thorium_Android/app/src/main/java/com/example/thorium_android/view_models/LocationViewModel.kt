package com.example.thorium_android.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.thorium_android.entities.Cell
import com.example.thorium_android.entities.LocData
import com.example.thorium_android.repositories.LocationRepository
import com.example.thorium_android.LocationDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(application: Application): AndroidViewModel(application) {

    private val allLocations: LiveData<List<LocData>>
    private val repository: LocationRepository

    init {
        val locDao = LocationDatabase.getInstance(application).locationDao()
        repository = LocationRepository(locDao)
        allLocations = repository.allLocations
    }

    fun addLocation(locData: LocData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLocation(locData)
        }
    }

    fun addCell(cell: Cell){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCell(cell)
        }
    }


}