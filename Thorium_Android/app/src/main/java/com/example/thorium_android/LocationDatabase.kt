package com.example.thorium_android

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thorium_android.entities.Cell
import com.example.thorium_android.entities.LocData
import com.example.thorium_android.entities.LocationDao

@Database(
    entities = [
        LocData::class,
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