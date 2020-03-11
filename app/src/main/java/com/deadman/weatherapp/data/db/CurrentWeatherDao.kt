package com.deadman.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.deadman.weatherapp.data.db.entity.Current
import com.deadman.weatherapp.data.db.entity.DEFAULT_CURRENT_WEATHER_ID

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(entry: Current)

    @Query("select * from current_weather where id = $DEFAULT_CURRENT_WEATHER_ID")
    fun getWeather(): LiveData<Current>
}