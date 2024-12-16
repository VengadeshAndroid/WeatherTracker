package com.nooro.weather.webservice

import android.content.Context
import com.nooro.weather.model.State
import com.nooro.weather.model.WeatherResponse
import com.nooro.weather.state.BaseState
import com.nooro.weather.util.sharedpreference.SharedPrefManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class ModelRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPrefManager: SharedPrefManager,
    private val apiClient: ApiClient
) {

    fun getCurrentWeather(
        apiKey: String? = null, location: String? = null,
        baseFlow: MutableSharedFlow<BaseState>?
    ): Flow<State<WeatherResponse>> {
        return object : NetworkBoundRepository<WeatherResponse>(
            baseFlow = baseFlow, sharedPrefManager = sharedPrefManager, context = context
        ) {
            override suspend fun fetchData() =
                apiClient.getApiClient().getCurrentWeather(apiKey, location)
        }.asFlow()
    }

}