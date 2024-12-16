package com.nooro.weather.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.nooro.weather.R
import com.nooro.weather.component.BaseComponent
import com.nooro.weather.component.NoDataFound
import com.nooro.weather.model.WeatherResponse
import com.nooro.weather.state.WeatherState
import com.nooro.weather.ui.theme.Color2C2C2C
import com.nooro.weather.ui.theme.Color9A9A9A
import com.nooro.weather.ui.theme.ColorC4C4C4
import com.nooro.weather.ui.theme.ColorF2F2F2
import com.nooro.weather.ui.theme.Dp10
import com.nooro.weather.ui.theme.Dp110
import com.nooro.weather.ui.theme.Dp125
import com.nooro.weather.ui.theme.Dp15
import com.nooro.weather.ui.theme.Dp16
import com.nooro.weather.ui.theme.Dp20
import com.nooro.weather.ui.theme.Dp24
import com.nooro.weather.ui.theme.Dp30
import com.nooro.weather.ui.theme.Dp4
import com.nooro.weather.ui.theme.Dp40
import com.nooro.weather.ui.theme.Dp5
import com.nooro.weather.ui.theme.Dp50
import com.nooro.weather.ui.theme.Dp70
import com.nooro.weather.ui.theme.Dp8
import com.nooro.weather.ui.theme.Dp80
import com.nooro.weather.ui.theme.Sp12
import com.nooro.weather.ui.theme.Sp15
import com.nooro.weather.ui.theme.Sp20
import com.nooro.weather.ui.theme.Sp30
import com.nooro.weather.ui.theme.Sp60
import com.nooro.weather.ui.theme.Sp70
import com.nooro.weather.ui.theme.Typography
import com.nooro.weather.util.ThemePreview
import com.nooro.weather.util.hasNetworkConnection
import com.nooro.weather.viewmodel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: HomeViewModel = hiltViewModel()

    var weatherResponse by remember { mutableStateOf<WeatherResponse?>(null) }

    val weatherDesign = remember { mutableStateOf(false) }

    val apiKey = "c396eae727254bf1bbe143605241512"

    val weatherShared by viewModel.savedWeatherResponse.collectAsState()

    var message = stringResource(id = R.string.no_city_selected)

    BaseComponent(viewModel = viewModel, stateObserver = { state ->
        when (state) {
            is WeatherState.WeatherSuccessState -> {
                weatherDesign.value = true
                weatherResponse = state.data
                viewModel.dismissProgressBar()
            }

            is WeatherState.ShowMessage -> {
                weatherDesign.value = false
                message = state.msg
                viewModel.saveWeatherResponse(null)
                viewModel.showToast(state.msg)
            }

            else -> {
                viewModel.dismissProgressBar()
            }
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFFFFFF))
        ) {

            Spacer(modifier = Modifier.height(Dp30))

            // Search bar
            SearchBar(viewModel, apiKey)

            if (weatherDesign.value) {
                // Show search result card
                weatherResponse?.let { result ->

                    Spacer(modifier = Modifier.height(Dp30))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = Dp20, end = Dp20),
                        shape = RoundedCornerShape(Dp16),
                        elevation = CardDefaults.cardElevation(Dp4),
                        colors = CardDefaults.cardColors(containerColor = ColorF2F2F2)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dp20)
                                .clickable {
                                    viewModel.showProgressBar()
                                    // On card click, save the weather response to SharedPreferences
                                    viewModel.saveWeatherResponse(result) // Save to SharedPreferences
                                    weatherDesign.value = false
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left side: Location and Temperature
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = result.location.name, // Location name
                                    style = Typography.displayMedium,
                                    fontSize = Sp20,
                                    color = Color2C2C2C
                                )
                                Spacer(modifier = Modifier.height(8.dp)) // Small gap
                                Row(
                                    verticalAlignment = Alignment.CenterVertically, // Aligns vertically centered
                                    horizontalArrangement = Arrangement.Start // Keeps text on the left and icon on the right
                                ) {
                                    Text(
                                        text = "${result.current.temp_c}", // Temperature
                                        style = Typography.labelMedium,
                                        fontSize = Sp60,
                                        color = Color2C2C2C
                                    )
                                    Spacer(modifier = Modifier.width(Dp10))
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_circle), // Replace with your drawable resource
                                        contentDescription = "Circle Icon",
                                        modifier = Modifier
                                            .size(Dp5)
                                            .offset(y = (-20).dp),
                                    )
                                }
                            }

                            // Right side: Weather Icon
                            AsyncImage(
                                model = "https:${result.current.condition.icon}",
                                contentDescription = "Weather Icon",
                                modifier = Modifier
                                    .width(Dp80)
                                    .height(Dp70)
                            )
                        }
                    }
                }
            } else {
                // Show the saved city's weather or prompt to search
                weatherShared.let {
                    viewModel.dismissProgressBar()
                    it?.let { it1 -> WeatherCard(weatherData = it1) }
                } ?: NoDataFound(message)
            }
        }

    }
}

@Composable
fun SearchBar(viewModel: HomeViewModel, apikey: String) {
    var searchText by remember { mutableStateOf("") }
    val context = LocalContext.current

    OutlinedTextField(
        value = searchText,
        onValueChange = { newText ->
            searchText = newText
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dp20, end = Dp20)
            .height(Dp50) // Reduced height
            .clip(RoundedCornerShape(20))
            .background(Color.LightGray.copy(alpha = 0.2f)),
        trailingIcon = {
            IconButton(onClick = {
                if (context.hasNetworkConnection()) {
                    if (searchText.length >= 4) {
                        viewModel.showProgressBar()
                        viewModel.getCurrentWeather(apiKey = apikey, location = searchText)
                    } else if (searchText.isEmpty()) {
                        viewModel.showToast("Please enter the search location.")
                    }
                } else {
                    viewModel.showToast("No network found. Please check your connection")
                }

            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Filter Icon",
                    modifier = Modifier
                        .size(Dp24)
                        .padding(end = Dp5)
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_location),
                style = Typography.titleMedium,
                fontSize = Sp15,
                color = ColorC4C4C4,
            )
        },

        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    )
}

@Composable
fun WeatherCard(weatherData: WeatherResponse) {
    // Main card containing weather details
    Box(
        modifier = Modifier
            .fillMaxSize() // Fills the entire screen
            .background(Color(0xFFFFFFFF)), contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .padding(Dp16)
                .background(Color(0xFFFFFFFF)) // Card background
                .fillMaxWidth(), // Card width
            horizontalAlignment = Alignment.CenterHorizontally // Align children horizontally to the center
        ) {

            Spacer(modifier = Modifier.height(Dp20))

            // Weather icon
            AsyncImage(
                model = "https:${weatherData.current.condition.icon}",
                contentDescription = "Weather Icon",
                modifier = Modifier
                    .width(Dp125)
                    .height(Dp110),
                contentScale = ContentScale.Fit

            )
            // Location and temperature
            Spacer(modifier = Modifier.height(Dp8))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, // Aligns vertically centered
                horizontalArrangement = Arrangement.Center // Keeps text on the left and icon on the right
            ) {

                Text(
                    text = weatherData.location.name, // Location name
                    style = Typography.displayMedium,
                    fontSize = Sp30,
                    color = Color2C2C2C,
                )
                Spacer(modifier = Modifier.width(Dp10))
                Image(
                    painter = painterResource(id = R.drawable.ic_navigation), // Replace with your drawable resource
                    contentDescription = "Navigation Icon",
                    modifier = Modifier.size(Dp20), // Set size for the icon
                )

            }

            Spacer(modifier = Modifier.height(Dp15))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, // Aligns vertically centered
                horizontalArrangement = Arrangement.Center // Keeps text on the left and icon on the right
            ) {
                Text(
                    text = "${weatherData.current.temp_c}", // Temperature
                    style = Typography.displayMedium,
                    fontSize = Sp70,
                    color = Color2C2C2C
                )
                Spacer(modifier = Modifier.width(Dp10))
                Image(
                    painter = painterResource(id = R.drawable.ic_circle), // Replace with your drawable resource
                    contentDescription = "Circle Icon",
                    modifier = Modifier
                        .size(Dp10)
                        .offset(y = (-20).dp),
                )
            }
            // Additional details like humidity, UV index, and feels like temperature
            Spacer(modifier = Modifier.height(Dp16))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dp40, end = Dp40),
                elevation = CardDefaults.cardElevation(Dp4),
                colors = CardDefaults.cardColors(containerColor = ColorF2F2F2)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dp10),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherDetail(
                        label = stringResource(id = R.string.humidity),
                        value = "${weatherData.current.humidity}%"
                    )
                    WeatherDetail(
                        label = stringResource(id = R.string.label_uv),
                        value = "${weatherData.current.uv}"
                    )
                    WeatherDetail(
                        label = stringResource(id = R.string.feels_like),
                        value = "${weatherData.current.feelslike_c} °"
                    )
                }
            }

        }
    }

}

@Composable
fun WeatherDetail(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = Typography.labelMedium,
            fontSize = Sp12,
            color = ColorC4C4C4
        )
        Spacer(modifier = Modifier.height(Dp15))
        Text(
            text = value,
            style = Typography.labelMedium,
            fontSize = Sp15,
            color = Color9A9A9A
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ThemePreview
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}