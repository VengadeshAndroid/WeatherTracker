package com.nooro.weather.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nooro.weather.view.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateNavigationGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.HOME) {

        composable(Screens.HOME) {
            HomeScreen(navController)
        }
    }
}
