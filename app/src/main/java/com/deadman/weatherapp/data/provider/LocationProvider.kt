package com.deadman.weatherapp.data.provider

import com.deadman.weatherapp.data.db.entity.Location

interface LocationProvider {
    suspend fun hasLocationChanged(lastLocation: Location): Boolean
    suspend fun getPrefferedLocationString(): String
}