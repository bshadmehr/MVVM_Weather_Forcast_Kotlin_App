package com.deadman.weatherapp.data.provider

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.deadman.weatherapp.data.db.entity.Location
import com.deadman.weatherapp.internal.LocationPremmissionNotGrantedException
import com.deadman.weatherapp.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import java.util.jar.Manifest

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl (
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
    ) : PrefrencesProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastLocation: Location): Boolean {

        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastLocation)
        } catch (e: LocationPremmissionNotGrantedException){
            false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastLocation)
    }

    override suspend fun getPrefferedLocationString(): String {
        if (isUsingDeviceLocation()){
            try {
                val deviceLocation = getLastDeviceLocation().await()
                    ?: return "${getCustomLocationName()}"
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            } catch (e: LocationPremmissionNotGrantedException){
                return "${getCustomLocationName()}"
            }
        } else {
            return "${getCustomLocationName()}"
        }
    }

    private fun hasCustomLocationChanged(lastLocation: Location): Boolean{
        val customLocationName = getCustomLocationName()
        return customLocationName != lastLocation.name
    }

    private fun getCustomLocationName(): String? {
        return prefrences.getString(CUSTOM_LOCATION, "Tehran")
    }

    private suspend fun hasDeviceLocationChanged(lastLocation: Location): Boolean{
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        val comparisonTreshhold = 0.03
        return Math.abs(deviceLocation.latitude - lastLocation.lat.toDouble()) > comparisonTreshhold &&
                Math.abs(deviceLocation.longitude - lastLocation.lon.toDouble()) > comparisonTreshhold
    }

    private fun isUsingDeviceLocation(): Boolean{
        return prefrences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getLastDeviceLocation(): Deferred<android.location.Location?>{
        return if (hasLocationPremmesion())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPremmissionNotGrantedException()
    }

    private fun hasLocationPremmesion(): Boolean{
        return ContextCompat.checkSelfPermission(appContext,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}