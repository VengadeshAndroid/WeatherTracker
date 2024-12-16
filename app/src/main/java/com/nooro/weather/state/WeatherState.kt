package com.nooro.weather.state

import com.nooro.weather.model.WeatherResponse


sealed class WeatherState {

    object Init : WeatherState()

    data class WeatherSuccessState(val data: WeatherResponse) : WeatherState()

    data class ShowMessage(val msg: String) : WeatherState()
}