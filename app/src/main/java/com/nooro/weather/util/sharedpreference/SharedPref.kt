package com.nooro.weather.util.sharedpreference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SharedPref @Inject constructor(@ApplicationContext private var context: Context) {

    /**
     * Singleton object for the shared preference.
     *
     * @param context Context of current state of the application/object
     * @return SharedPreferences object is returned.
     */

    private var preference: SharedPreferences? = null

    private fun getPreferenceInstance(): SharedPreferences? {
        return if (preference != null) {
            preference
        } else {
            preference = context.getSharedPreferences(context.packageName, MODE_PRIVATE)
            preference
        }
    }

    /**
     * Set the String value in the shared preference W.R.T the given key.
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @param value   String value which is to be stored in shared preference.
     */

    fun setSharedValue(key: String, value: String?) {
        getPreferenceInstance()
        val editor = preference?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    /**
     * Returns String value for the given key.
     * By default it will return null.
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @return null by default; returns the String value for the given key.
     */

    fun getStringValue(key: String): String {
        getPreferenceInstance()
        return preference?.getString(key, "") ?: ""
    }

    fun removeSharedValue(key: String) {
        preference?.edit()?.remove(key)?.apply()
    }

    fun clear() {
        preference?.edit()?.clear()?.apply()
    }

}