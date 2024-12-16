package com.nooro.weather.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Parcelable
import android.provider.Settings

fun Context.hasNetworkConnection(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var isConnected = false
    // Retrieve current status of connectivity
    connectivityManager.allNetworks.forEach { network ->
        val networkCapability = connectivityManager.getNetworkCapabilities(network)
        networkCapability?.let {
            if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                isConnected = true
                return@forEach
            }
        }
    }
    return isConnected
}

fun Context.showNetworkSettings() {
    val chooserIntent = Intent.createChooser(
        getSettingsIntent(Settings.ACTION_DATA_ROAMING_SETTINGS),
        "Complete action using"
    )
    val networkIntents = ArrayList<Intent>()
    networkIntents.add(getSettingsIntent(Settings.ACTION_WIFI_SETTINGS))
    chooserIntent.putExtra(
        Intent.EXTRA_INITIAL_INTENTS,
        networkIntents.toTypedArray<Parcelable>()
    )
    chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivityBySettings(this, chooserIntent)
}

private fun getSettingsIntent(settings: String): Intent {
    return Intent(settings)
}

private fun startActivityBySettings(context: Context, intent: Intent) {
    context.startActivity(intent)
}




