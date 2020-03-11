package com.deadman.weatherapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.deadman.weatherapp.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl (context: Context) : PrefrencesProvider(context), UnitProvider {
    override fun getUnitSystem(): UnitSystem {
        val selectedName = prefrences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return  UnitSystem.valueOf(selectedName!!)
    }
}