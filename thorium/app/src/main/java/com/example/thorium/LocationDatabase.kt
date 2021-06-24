package com.example.thorium

import android.content.Context
import android.icu.util.LocaleData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thorium.entities.Cell
import com.example.thorium.entities.Location
import com.example.thorium.entities.LocationDao

@Database(
    entities = [
        Location::class,
        Cell::class
    ],
    version = 1
)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        @Volatile
        private var INSTANCE: LocationDatabase? = null

        fun getInstance(context: Context): LocationDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    "location_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}