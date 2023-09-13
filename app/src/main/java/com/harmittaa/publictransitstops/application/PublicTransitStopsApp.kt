package com.harmittaa.publictransitstops.application

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.harmittaa.publictransitstops.extensions.checkIfAppHasLocationPermission
import com.harmittaa.publictransitstops.ui.screen.Screen
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreen
import com.harmittaa.publictransitstops.ui.screen.permission.LocationPermissionScreen
import com.harmittaa.publictransitstops.ui.theme.PublicTransitStopsTheme

@Composable
fun PublicTransitStopsApp() {
    PublicTransitStopsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val context = LocalContext.current
            // define start screen: show permission request screen if required
            // app is always restarted if permissions are later denied, hence this is always checked
            val startScreen: Screen = when (PackageManager.PERMISSION_GRANTED) {
                context.checkIfAppHasLocationPermission() -> Screen.NearestStops
                else -> Screen.LocationPermission
            }

            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = startScreen.route
            ) {
                composable(Screen.LocationPermission.route) {
                    LocationPermissionScreen {
                        navController.navigate(Screen.NearestStops.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                }
                composable(Screen.NearestStops.route) { NearestStopsScreen() }
            }
        }
    }
}
