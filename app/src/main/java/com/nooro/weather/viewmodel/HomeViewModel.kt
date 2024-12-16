package com.nooro.weather.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.nooro.weather.model.State
import com.nooro.weather.model.WeatherResponse
import com.nooro.weather.state.WeatherState
import com.nooro.weather.util.sharedpreference.SharedPrefManager
import com.nooro.weather.webservice.ModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val modelRepository: ModelRepository,
                                        private val sharedPrefManager: SharedPrefManager) :
    BaseViewModel<WeatherState>() {

    private var setWeatherState: WeatherState = WeatherState.Init
        set(value) {
            field = value
            setState(setWeatherState)
        }

    val gson = Gson()

    private val _savedWeatherResponse = MutableStateFlow<WeatherResponse?>(null)
    val savedWeatherResponse: StateFlow<WeatherResponse?> = _savedWeatherResponse

    init {
        _savedWeatherResponse.value = getSavedWeatherResponse()
    }


    fun getCurrentWeather(apiKey: String? = null, location: String? = null) {
        viewModelScope.launch {
            modelRepository.getCurrentWeather(apiKey, location, _baseState).collect {
                when (it) {
                    is State.Success -> {
                        setWeatherState = WeatherState.WeatherSuccessState(it.data)
                    }

                    is State.Error -> {
                        setWeatherState = WeatherState.ShowMessage(it.detail)
                    }

                    else -> {
                        dismissProgressBar()
                    }
                }
            }
        }
    }

    // Save WeatherResponse
    fun saveWeatherResponse(weatherResponse: WeatherResponse?) {
        if (weatherResponse == null) {
            // Clear the saved weather response
            sharedPrefManager.weatherResponse = "" // Clear the JSON string in SharedPreferences
            _savedWeatherResponse.value = null // Update StateFlow to null
        } else {
            // Save the weather response
            val json = gson.toJson(weatherResponse) // Convert to JSON
            sharedPrefManager.weatherResponse = json // Save JSON string to SharedPreferences
            _savedWeatherResponse.value = weatherResponse // Update StateFlow with the new value
        }
    }

    // Retrieve WeatherResponse
    fun getSavedWeatherResponse(): WeatherResponse? {
        val json = sharedPrefManager.weatherResponse // Retrieve JSON string
        return if (json.isNotEmpty()) gson.fromJson(json, WeatherResponse::class.java) else null
    }

}