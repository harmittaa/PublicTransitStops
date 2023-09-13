package com.harmittaa.publictransitstops.ui.screen.nearestStops

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.koin.androidx.compose.koinViewModel

@Composable
fun NearestStopsScreen(viewModel: NearestStopsScreenViewModel = koinViewModel()) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nearest stops")
    }
}