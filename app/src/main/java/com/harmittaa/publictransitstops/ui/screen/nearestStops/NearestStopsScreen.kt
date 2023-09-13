package com.harmittaa.publictransitstops.ui.screen.nearestStops

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harmittaa.publictransitstops.StopsByRadiusQuery
import com.harmittaa.publictransitstops.ui.screen.Screen
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState.Error
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState.Loading
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState.Success
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun NearestStopsScreen(
    viewModel: NearestStopsScreenViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    when (state.currentState) {
        is Error -> {
            ErrorView(error = state.currentState as Error)
        }

        is Loading -> {
            LoadingView()
        }

        is Success -> {
            NearestStopsView(state.data)
        }
    }
}


@Composable
fun NearestStopsView(data: List<StopsByRadiusQuery.Node>) {
    LazyColumn {
        items(data) { node ->
            NearestStopListItem(name = node.stop?.name!!, distance = node.distance!!)
        }
    }
}

@Composable
fun NearestStopListItem(name: String, distance: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Stop: $name")
        Text(text = "Distance: $distance")
    }
}

@Composable
fun ErrorView(error: Error) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "There has been an error")
        Text(text = "$error")
    }
}

@Composable
fun LoadingView() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Loading data... please hold on")
    }
}