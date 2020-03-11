package com.deadman.weatherapp.data.network

import androidx.lifecycle.LiveData
import com.deadman.weatherapp.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        unit: String
    )
}