package com.harmittaa.publictransitstops.network

import com.harmittaa.publictransitstops.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Injects required header for Digitransit subscription key to each outgoing request.
 * Header value is fetched from BuildConfig. See project README.md for more information.
 */
class AuthInterceptor : Interceptor {
    private val authHeader = "digitransit-subscription-key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                addHeader(authHeader, BuildConfig.DIGITRANSIT_KEY)
            }
            .build()
        return chain.proceed(request)
    }

}