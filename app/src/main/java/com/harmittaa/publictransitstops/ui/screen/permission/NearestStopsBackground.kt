package com.harmittaa.publictransitstops.ui.screen.permission

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harmittaa.publictransitstops.model.Stop
import com.harmittaa.publictransitstops.ui.screen.nearestStops.composables.NearestStopListItem
import com.harmittaa.publictransitstops.ui.theme.PublicTransitStopsTheme

private val mockStops = listOf(
    Stop(
        name = "Asemapäällikönkatu",
        distance = 120
    ),
    Stop(
        name = "Pasilan asema",
        distance = 321
    ),
    Stop(
        name = "Messukeskus",
        distance = 409
    ),
    Stop(
        name = "Radanrakentajantie",
        distance = 678
    )
)

@Composable
fun NearestStopsBackground(
    stops: List<Stop> = mockStops
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        stops.forEach { stop ->
            NearestStopListItem(name = stop.name, distance = stop.distance)
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
        }
    }
}

@Preview
@Composable
fun PreviewNearestStopsBackground() {
    PublicTransitStopsTheme {
        NearestStopsBackground()
    }
}
