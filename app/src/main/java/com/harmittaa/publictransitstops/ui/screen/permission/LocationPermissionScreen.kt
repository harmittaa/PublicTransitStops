package com.harmittaa.publictransitstops.ui.screen.permission

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harmittaa.publictransitstops.extensions.checkIfAppHasLocationPermission
import com.harmittaa.publictransitstops.ui.theme.PublicTransitStopsTheme

@Composable
fun LocationPermissionScreen(
    navigate: () -> Unit
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

    var columnHeightPx by remember {
        mutableFloatStateOf(0f)
    }

    NearestStopsBackground()
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    0F to Color.Transparent,
                    .5F to MaterialTheme.colorScheme.background,
                    .9F to MaterialTheme.colorScheme.background,
                    startY = 1f,
                    // makes sure that the gradient goes over the background
                    // and the content of the screen does not overlap
                    endY = columnHeightPx * 0.8f
                )
            )
            .onGloballyPositioned { coordinates ->
                columnHeightPx = coordinates.size.height.toFloat()
                println("Setting height to $columnHeightPx")
            }

    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight(0.7f)
            ) {
                Text(
                    text = "To use this application, you will need to provide your location.",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = {
                        when (PackageManager.PERMISSION_GRANTED) {
                            context.checkIfAppHasLocationPermission() ->
                                println("App has been granted permissions")

                            else -> launcher.launch(ACCESS_FINE_LOCATION)
                        }
                    }
                ) {
                    Text(
                        text = "Allow location permission",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLocationPermissionScreen() {
    PublicTransitStopsTheme {
        LocationPermissionScreen(navigate = {})
    }
}
