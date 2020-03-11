package com.deadman.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import com.deadman.weatherapp.data.provider.LocationProvider
import com.deadman.weatherapp.data.provider.UnitProvider
import com.deadman.weatherapp.data.repository.WeatherRepository
import com.deadman.weatherapp.internal.UnitSystem
import com.deadman.weatherapp.internal.lazyDeffered

class CurrentWeatherViewModel (
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider,
    unitProvider: UnitProvider
): ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetrics: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeffered {
        weatherRepository.getCurrentWeather(isMetrics)
    }

    val weatherLocation by lazyDeffered {
        weatherRepository.getWeatherLocation()
    }
}
