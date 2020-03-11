package com.deadman.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deadman.weatherapp.data.db.convertors.ListArrayTypeConvertor
import com.deadman.weatherapp.data.db.entity.Current
import com.deadman.weatherapp.data.db.entity.Location

@Database(
    entities = [Current::class, Location::class],
    version = 1
)
@TypeConverters(ListArrayTypeConvertor::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun locationWeatherDa(): LocationWeatherDao

    companion object {
        @Volatile private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java, "weather.db")
                .build()
    }

}