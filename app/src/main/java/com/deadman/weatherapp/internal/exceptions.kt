package com.deadman.weatherapp.internal

import java.io.IOException
import java.lang.Exception

class NoConnectivityException: IOException()
class LocationPremmissionNotGrantedException: Exception()