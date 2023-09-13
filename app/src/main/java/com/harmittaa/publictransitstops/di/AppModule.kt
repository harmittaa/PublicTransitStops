package com.harmittaa.publictransitstops.di

import com.harmittaa.publictransitstops.location.getLocationProvider
import com.harmittaa.publictransitstops.network.AuthInterceptor
import com.harmittaa.publictransitstops.network.getDigitransitClient
import com.harmittaa.publictransitstops.repository.LocationRepository
import com.harmittaa.publictransitstops.repository.StopsRepository
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // networking
    factory { AuthInterceptor() }
    single { getDigitransitClient(authInterceptor = get()) }
    factory { StopsRepository(api = get()) }

    // location
    factory { getLocationProvider(context = get()) }
    factory { LocationRepository(locationClient = get()) }

    // presentation
    viewModel {
        NearestStopsScreenViewModel(
            locationRepository = get(),
            stopsRepository = get()
        )
    }
}
