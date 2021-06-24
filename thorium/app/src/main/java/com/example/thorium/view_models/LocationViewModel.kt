package com.example.thorium.view_models

import android.app.Application
import android.icu.util.LocaleData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.thorium.LocationDatabase
import com.example.thorium.entities.Cell
import com.example.thorium.entities.Location
import com.example.thorium.repositories.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(application: Application): AndroidViewModel(application) {

    private val allLocations: LiveData<List<Location>>
    private val repository: LocationRepository

    init {
        val locDao = LocationDatabase.getInstance(application).locationDao()
        repository = LocationRepository(locDao)
        allLocations = repository.allLocations
    }

    fun addLocation(location: Location){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLocation(location)
        }
    }

    fun addCell(cell: Cell){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCell(cell)
        }
    }


}