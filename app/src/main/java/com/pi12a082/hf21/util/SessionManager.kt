package com.pi12a082.hf21.util

import android.content.Context
import android.content.SharedPreferences
import com.pi12a082.hf21.R
import com.pi12a082.hf21.util.Constants.ACCESS_TOKEN
import com.pi12a082.hf21.util.Constants.REFRESH_TOKEN

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)


    /**
     * Function to save access token
     */
    fun saveAccessToken(token: String) {
        val editor = prefs.edit()
        editor.putString(ACCESS_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch access token
     */
    fun fetchAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN, null)
    }

    fun saveRefreshToken(token: String) {
        val editor = prefs.edit()
        editor.putString(REFRESH_TOKEN, token)
        editor.apply()
    }

    fun fetchRefreshToken(): String? {
        return prefs.getString(REFRESH_TOKEN, null)
    }

}