package com.harmittaa.publictransitstops.di

import com.harmittaa.publictransitstops.network.AuthInterceptor
import com.harmittaa.publictransitstops.network.getDigitransitClient
import org.koin.dsl.module

val appModule = module {
    factory { AuthInterceptor() }
    single { getDigitransitClient(authInterceptor = get()) }
}