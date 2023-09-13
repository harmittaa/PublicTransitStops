package com.harmittaa.publictransitstops.ui.screen.nearestStops

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState.Error
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState.Loading
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState.Success
import com.harmittaa.publictransitstops.ui.screen.nearestStops.composables.ErrorView
import com.harmittaa.publictransitstops.ui.screen.nearestStops.composables.LoadingView
import com.harmittaa.publictransitstops.ui.screen.nearestStops.composables.NearestStopsView
import org.koin.androidx.compose.koinViewModel

@Composable
fun NearestStopsScreen(
    viewModel: NearestStopsScreenViewModel = koinViewModel()
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
