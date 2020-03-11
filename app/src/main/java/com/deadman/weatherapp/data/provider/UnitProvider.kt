package com.deadman.weatherapp.data.provider

import com.deadman.weatherapp.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}