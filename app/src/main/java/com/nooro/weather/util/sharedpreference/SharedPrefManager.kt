package com.nooro.weather.util.sharedpreference

import com.nooro.weather.util.Constants.SharedKey.WEATHER
import javax.inject.Inject


class SharedPrefManager @Inject constructor(private val sharedPref: SharedPref) {

    fun clearData() {
        sharedPref.clear()
    }

    var weatherResponse: String
        get() = sharedPref.getStringValue(WEATHER)
        set(weatherResponse) = sharedPref.setSharedValue(WEATHER, weatherResponse)

}