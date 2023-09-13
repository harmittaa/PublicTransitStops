package com.harmittaa.publictransitstops.ui.screen

interface Screen {
    val route: String

    object LocationPermission : Screen {
        override val route: String = "PermissionScreen"
    }

    object NearestStops : Screen {
        override val route: String = "NearestStopsScreen"
    }
}
