package com.deadman.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.deadman.weatherapp.data.db.entity.Current
import com.deadman.weatherapp.data.db.entity.Location

interface WeatherRepository {
    suspend fun getCurrentWeather (metrics: Boolean): LiveData<Current>
    suspend fun getWeatherLocation (): LiveData<Location>
}