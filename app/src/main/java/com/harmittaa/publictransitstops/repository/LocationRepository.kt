package com.harmittaa.publictransitstops.repository

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class LocationRepository(
    private val locationClient: FusedLocationProviderClient
) {

    // permission is already checked on app launch
    // denying permission later forces app restart, hence this won't be reached without permission
    @SuppressLint("MissingPermission")
    fun getLocation(): Flow<Location?> = callbackFlow {
        locationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                trySend(location)
            }

        awaitClose()
    }
}
