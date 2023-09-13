package com.harmittaa.publictransitstops.location

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

fun getLocationProvider(context: Context): FusedLocationProviderClient {
    return LocationServices.getFusedLocationProviderClient(context)
}