package com.deadman.weatherapp.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val DEFAULT_LOCATION_ID = 0

@Entity(tableName = "current_location")
data class Location(
    val country: String,
    val lat: String,
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    val lon: String,
    val name: String,
    val region: String,
    @SerializedName("timezone_id")
    val timezoneId: String,
    @SerializedName("utc_offset")
    val utcOffset: String,
    var requestTime: Int
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = DEFAULT_LOCATION_ID
}