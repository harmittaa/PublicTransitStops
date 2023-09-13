package com.harmittaa.publictransitstops.di

import com.harmittaa.publictransitstops.network.AuthInterceptor
import com.harmittaa.publictransitstops.network.getDigitransitClient
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { AuthInterceptor() }
    single { getDigitransitClient(authInterceptor = get()) }
    viewModel { NearestStopsScreenViewModel(api = get()) }
}