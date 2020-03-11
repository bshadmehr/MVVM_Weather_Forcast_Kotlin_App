package com.deadman.weatherapp

import android.app.Application
import android.content.Context
import com.deadman.weatherapp.data.db.WeatherDatabase
import com.deadman.weatherapp.data.network.*
import com.deadman.weatherapp.data.provider.LocationProvider
import com.deadman.weatherapp.data.provider.LocationProviderImpl
import com.deadman.weatherapp.data.provider.UnitProvider
import com.deadman.weatherapp.data.provider.UnitProviderImpl
import com.deadman.weatherapp.data.repository.WeatherRepository
import com.deadman.weatherapp.data.repository.WeatherRepositoryImpl
import com.deadman.weatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatherApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }
        bind() from singleton { instance<WeatherDatabase>().locationWeatherDa() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<WeatherRepository>() with singleton { WeatherRepositoryImpl(instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}