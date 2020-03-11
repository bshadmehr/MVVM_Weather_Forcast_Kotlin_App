package com.deadman.weatherapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

abstract class PrefrencesProvider (context: Context) {
    private val appContext = context.applicationContext

    protected val prefrences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}