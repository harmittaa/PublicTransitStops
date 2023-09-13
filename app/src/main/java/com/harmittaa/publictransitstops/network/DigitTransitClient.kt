package com.harmittaa.publictransitstops.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.apollographql.apollo3.network.okHttpClient
import com.harmittaa.publictransitstops.BuildConfig
import okhttp3.OkHttpClient

fun getDigitransitClient(authInterceptor: AuthInterceptor) = ApolloClient.Builder()
    .serverUrl(BuildConfig.DIGITRANSIT_URL)
    // NOTE! This will print out API key, use only for local development purposes.
    .addHttpInterceptor(LoggingInterceptor(LoggingInterceptor.Level.BODY))
    .okHttpClient(
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    )
    .build()
