package com.harmittaa.publictransitstops.ui.screen.nearestStops

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harmittaa.publictransitstops.extensions.toLocalDataOrNull
import com.harmittaa.publictransitstops.model.Stop
import com.harmittaa.publictransitstops.repository.LocationRepository
import com.harmittaa.publictransitstops.repository.NetworkResult
import com.harmittaa.publictransitstops.repository.StopsRepository
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class NearestStopsScreenViewModel(
    locationRepository: LocationRepository,
    private val stopsRepository: StopsRepository
) : ViewModel() {

    private val wakeup = MutableStateFlow(false)
    private val location: Flow<Location?> = wakeup.flatMapLatest { locationRepository.getLocation() }
    private val stops: Flow<NetworkResult> = location.flatMapLatest { location ->
        if (location == null) return@flatMapLatest flowOf(NetworkResult.NoLocation)
        stopsRepository.getNearbyStops(
            lat = location.latitude,
            lon = location.longitude
        )
    }

    private val _state: StateFlow<NearestStopsState> = combine(location, stops) { location, stops ->
        if (location == null) return@combine NearestStopsState(currentState = Error.Location)

        when (stops) {
            is NetworkResult.Error -> NearestStopsState(currentState = Error.Network)
            is NetworkResult.NoLocationsNearby -> NearestStopsState(
                currentState = Error.NoStopsNearby
            )

            is NetworkResult.Success -> NearestStopsState(
                currentState = UIState.Success,
                data = stops.data.mapNotNull { it.toLocalDataOrNull() }
            )

            NetworkResult.NoLocation -> NearestStopsState(
                currentState = Error.NoStopsNearby
            )
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = NearestStopsState()
    )

    val state: StateFlow<NearestStopsState> = _state

    fun onScreenStarted() {
        wakeup.value = true
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
