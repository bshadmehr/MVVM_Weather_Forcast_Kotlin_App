package com.deadman.weatherapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deadman.weatherapp.data.network.response.CurrentWeatherResponse
import com.deadman.weatherapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl (
    private val weatherApiService: WeatherApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, unit: String) {
        try {
            val fetchedCurrentWeather = weatherApiService
                .getCurrentWeather(location, unit)
                .await()
            fetchedCurrentWeather.current.unit = unit
            fetchedCurrentWeather.location.requestTime = (System.currentTimeMillis() / 1000).toInt()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException){
            Log.e("connectivity", "No Internet Connection")
        }
    }
}