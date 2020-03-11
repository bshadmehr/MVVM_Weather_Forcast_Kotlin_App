package com.deadman.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deadman.weatherapp.data.db.entity.DEFAULT_LOCATION_ID
import com.deadman.weatherapp.data.db.entity.Location

@Dao
interface LocationWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(entry: Location)

    @Query("select * from current_location where id = $DEFAULT_LOCATION_ID")
    fun getCurentLocation(): LiveData<Location>
}