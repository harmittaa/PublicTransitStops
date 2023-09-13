package com.harmittaa.publictransitstops.ui.screen.nearestStops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harmittaa.publictransitstops.StopsByRadiusQuery
import com.harmittaa.publictransitstops.repository.LocationRepository
import com.harmittaa.publictransitstops.repository.NetworkRequestState
import com.harmittaa.publictransitstops.repository.StopsRepository
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState
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
                        _uiState.value = NearestStopsState(currentState = UIState.Error.Location)
                        return@collect
                    }
                    stopsRepository.getNearbyStops(
                        lat = location.latitude,
                        lon = location.longitude
                    ).collect { networkState ->
                        _uiState.value = when (networkState) {
                            is NetworkRequestState.ERROR -> NearestStopsState(currentState = UIState.Error.Network)
                            is NetworkRequestState.NO_LOCATIONS_NEARBY -> NearestStopsState(
                                currentState = UIState.Error.NoStopsNearby
                            )

                            is NetworkRequestState.SUCCESS -> NearestStopsState(
                                currentState = UIState.Success,
                                data = networkState.data
                            )
                        }
                    }
                }
        }
    }

    data class NearestStopsState(
        val currentState: UIState = UIState.Loading,
        val data: List<StopsByRadiusQuery.Node> = emptyList()
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
