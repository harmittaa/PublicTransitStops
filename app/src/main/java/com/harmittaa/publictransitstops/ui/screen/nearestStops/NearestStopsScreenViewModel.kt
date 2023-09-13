package com.harmittaa.publictransitstops.ui.screen.nearestStops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harmittaa.publictransitstops.extensions.toLocalDataOrNull
import com.harmittaa.publictransitstops.model.Stop
import com.harmittaa.publictransitstops.repository.LocationRepository
import com.harmittaa.publictransitstops.repository.NetworkResult
import com.harmittaa.publictransitstops.repository.StopsRepository
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState.Error
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NearestStopsScreenViewModel(
    locationRepository: LocationRepository,
    private val stopsRepository: StopsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NearestStopsState())

    val uiState: StateFlow<NearestStopsState> = _uiState

    init {
        viewModelScope.launch {
            locationRepository.getLocation()
                .collect { location ->
                    if (location == null) {
                        _uiState.value = NearestStopsState(currentState = Error.Location)
                        return@collect
                    }
                    stopsRepository.getNearbyStops(
                        lat = location.latitude,
                        lon = location.longitude
                    ).collect { networkState ->
                        _uiState.value = when (networkState) {
                            is NetworkResult.Error -> NearestStopsState(currentState = Error.Network)
                            is NetworkResult.NoLocationsNearby -> NearestStopsState(
                                currentState = Error.NoStopsNearby
                            )

                            is NetworkResult.Success -> NearestStopsState(
                                currentState = UIState.Success,
                                data = networkState.data.mapNotNull { it.toLocalDataOrNull() }
                            )
                        }
                    }
                }
        }
    }

    data class NearestStopsState(
        val currentState: UIState = UIState.Loading,
        val data: List<Stop> = emptyList()
    ) {
        interface UIState {
            object Success : UIState
            object Loading : UIState

            enum class Error : UIState {
                Network,
                Location,
                NoStopsNearby
            }
        }
    }
}
