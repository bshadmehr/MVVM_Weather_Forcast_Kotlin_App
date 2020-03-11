package com.deadman.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.deadman.weatherapp.data.db.CurrentWeatherDao
import com.deadman.weatherapp.data.db.LocationWeatherDao
import com.deadman.weatherapp.data.db.entity.Current
import com.deadman.weatherapp.data.db.entity.Location
import com.deadman.weatherapp.data.network.WeatherNetworkDataSource
import com.deadman.weatherapp.data.network.response.CurrentWeatherResponse
import com.deadman.weatherapp.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class WeatherRepositoryImpl (
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationWeatherDao: LocationWeatherDao,
    private val locationProvider: LocationProvider
): WeatherRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(metrics: Boolean): LiveData<Current> {
        return withContext(Dispatchers.IO) {
            val unit: String
            unit = getCurrentUnitStr(metrics)

            initCurrentWeather(unit)
            return@withContext currentWeatherDao.getWeather()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<Location> {
        return withContext(Dispatchers.IO) {
            return@withContext locationWeatherDao.getCurentLocation()
        }
    }

    private fun getCurrentUnitStr(metrics: Boolean): String{
        if (metrics) return "m"
        return "f"
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch (Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.current)
            locationWeatherDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initCurrentWeather(unit: String) {
        if (locationWeatherDao.getCurentLocation().value == null || locationProvider.hasLocationChanged(locationWeatherDao.getCurentLocation().value!!)){
            fetchCurrentWeather(unit)
            return
        }
        if (isFetchCurrentNeeded(locationWeatherDao.getCurentLocation().value!!.requestTime)
            || weatherNetworkDataSource.downloadedCurrentWeather.value?.current?.unit != unit) {
            fetchCurrentWeather(unit)
        }
    }

    private suspend fun fetchCurrentWeather(unit: String) {
        val x = locationProvider.getPrefferedLocationString()
        weatherNetworkDataSource.fetchCurrentWeather(
            x,
            unit
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: Int): Boolean{

        val tsLong = System.currentTimeMillis() / 1000
        val thirtyMinAgo = tsLong - (30 * 60)
        if (lastFetchTime < thirtyMinAgo) return true
        return false
    }
}