package com.harmittaa.publictransitstops.ui.screen

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import android.content.Context


@Composable
fun LocationPermissionScreen(
    navigate: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            navigate()
        } else {
            println("PERMISSION DENIED")
        }
    }
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "To use this application, you will need to provide your location.")
        Button(
            onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    context.checkIfAppHasLocationPermission() -> println("Code requires permission")
                    else -> launcher.launch(ACCESS_FINE_LOCATION)
                }
            }
        ) {
            Text(text = "Check and Request Permission")
        }
    }
}

fun Context.checkIfAppHasLocationPermission(): Int {
    return ContextCompat.checkSelfPermission(
        this,
        ACCESS_FINE_LOCATION
    ) or ContextCompat.checkSelfPermission(
        this,
        ACCESS_COARSE_LOCATION
    )
}