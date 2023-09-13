package com.harmittaa.publictransitstops.extensions

import android.Manifest
import android.content.Context
import androidx.core.content.ContextCompat

fun Context.checkIfAppHasLocationPermission(): Int {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) or ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
}
