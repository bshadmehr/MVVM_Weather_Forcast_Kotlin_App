package com.deadman.weatherapp.data.db.entity


import androidx.room.*
import com.google.gson.annotations.SerializedName

const val DEFAULT_CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class  Current(
    val cloudcover: Int,
    val feelslike: Int,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("observation_time")
    val observationTime: String,
    val precip: Double,
    val pressure: Double,
    val temperature: Double,
    @SerializedName("uv_index")
    val uvIndex: Double,
    val visibility: Double,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>,
    @SerializedName("weather_icons")
    val weatherIcons: List<String>,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    var unit: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = DEFAULT_CURRENT_WEATHER_ID
}