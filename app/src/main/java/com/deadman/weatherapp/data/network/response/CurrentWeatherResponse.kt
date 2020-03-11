package com.deadman.weatherapp.data.network.response

import com.deadman.weatherapp.data.db.entity.Current
import com.deadman.weatherapp.data.db.entity.Location
import com.deadman.weatherapp.data.db.entity.Request


data class CurrentWeatherResponse(
    val current: Current,
    val location: Location,
    val request: Request
)