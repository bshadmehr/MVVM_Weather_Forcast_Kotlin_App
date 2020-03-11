package com.deadman.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deadman.weatherapp.data.provider.LocationProvider
import com.deadman.weatherapp.data.provider.UnitProvider
import com.deadman.weatherapp.data.repository.WeatherRepository

class CurrentWeatherViewModelFactory (
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(weatherRepository, locationProvider, unitProvider) as T
    }
}