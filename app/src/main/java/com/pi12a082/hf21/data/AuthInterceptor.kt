package com.pi12a082.hf21.data

import android.content.Context
import android.util.Log
import com.pi12a082.hf21.util.Constants.ACCESS_TOKEN
import com.pi12a082.hf21.util.Constants.REFRESH_TOKEN
import com.pi12a082.hf21.util.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sessionManager.fetchAccessToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        sessionManager.fetchRefreshToken()?.let {
            requestBuilder.addHeader("x-refresh", it)
        }

        val response = chain.proceed(requestBuilder.build())
        getTokensFromResHeaders(response)

        return response
    }

    private fun getTokensFromResHeaders(response: Response) {
        // headers() ではなく headers プロパティを使う
        var accessToken = response.headers[ACCESS_TOKEN]
        val refreshToken = response.headers[REFRESH_TOKEN]
        val newAccessToken = response.headers["x-access-token"]

        if (newAccessToken != null) accessToken = newAccessToken

        if (accessToken != null && refreshToken != null) {
            sessionManager.saveAccessToken(accessToken)
            sessionManager.saveRefreshToken(refreshToken)
            Log.d("getTokensFromResHeaders: ", "access: $accessToken")
        } else {
            Log.d("getTokensFromResponseHeaders: ", "Not token ${response.headers}")
        }
    }


}