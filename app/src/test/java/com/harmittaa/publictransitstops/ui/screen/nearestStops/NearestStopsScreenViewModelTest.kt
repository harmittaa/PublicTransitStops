package com.harmittaa.publictransitstops.ui.screen.nearestStops

import android.location.Location
import app.cash.turbine.test
import com.harmittaa.publictransitstops.StopsByRadiusQuery
import com.harmittaa.publictransitstops.repository.LocationRepository
import com.harmittaa.publictransitstops.repository.NetworkResult
import com.harmittaa.publictransitstops.repository.StopsRepository
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NearestStopsScreenViewModelTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val mockkRule = MockKRule(this)

    private lateinit var viewModel: NearestStopsScreenViewModel

    @RelaxedMockK
    private lateinit var locationRepository: LocationRepository

    @RelaxedMockK
    private lateinit var stopsRepository: StopsRepository

    @RelaxedMockK
    private lateinit var mockLocation: Location

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = NearestStopsScreenViewModel(
            locationRepository = locationRepository,
            stopsRepository = stopsRepository
        )
    }

    @Test
    fun `when VM is initialised, then first state is Loading`() = runBlocking {
        viewModel.state.test {
            assertEquals(UIState.Loading, awaitItem().currentState)
        }
    }

    @Test
    fun `when location returns null, then emit Location error`() = runTest {
        // given
        val locationFlow = MutableStateFlow<Location?>(null)
        every { locationRepository.getLocation() } returns locationFlow

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        // when
        viewModel.state.test {
            assertEquals(UIState.Loading, awaitItem().currentState)
            locationFlow.emit(mockLocation)
            assertEquals(UIState.Error.Location, awaitItem().currentState)
        }
    }

    @Test
    fun `when network returns error, then emit Network error`() = runTest {
        // given
        val locationFlow = MutableStateFlow<Location?>(mockLocation)
        every { locationRepository.getLocation() } returns locationFlow

        val networkResponse = NetworkResult.Error
        val stopFlow = MutableStateFlow<NetworkResult>(networkResponse)
        every { stopsRepository.getNearbyStops(any(), any(), any()) } returns stopFlow

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        // when
        locationFlow.emit(mockLocation)
        stopFlow.emit(networkResponse)
        val actualState = viewModel.state.drop(1).first().currentState

        // then
        assertEquals(UIState.Error.Network, actualState)
    }

    @Test
    fun `when network returns no location, then emit No Locations error`() = runTest {
        // given
        val locationFlow = MutableStateFlow<Location?>(mockLocation)
        every { locationRepository.getLocation() } returns locationFlow

        val networkResponse = NetworkResult.NoLocation
        val stopFlow = MutableStateFlow<NetworkResult>(networkResponse)
        every { stopsRepository.getNearbyStops(any(), any(), any()) } returns stopFlow

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        // when
        locationFlow.emit(mockLocation)
        stopFlow.emit(networkResponse)
        val actualState = viewModel.state.drop(1).first().currentState

        // then
        assertEquals(UIState.Error.NoStopsNearby, actualState)
    }

    @Test
    fun `when network returns locations, then those locations are emitted`() = runTest {
        // given
        val locationFlow = MutableStateFlow<Location?>(mockLocation)
        every { locationRepository.getLocation() } returns locationFlow

        val expectedStopList = listOf(
            StopsByRadiusQuery.Node(
                stop = StopsByRadiusQuery.Stop(name = "name"),
                distance = 100
            )
        )

        val networkResponse = NetworkResult.Success(data = expectedStopList)

        val stopFlow = MutableStateFlow<NetworkResult>(networkResponse)
        every { stopsRepository.getNearbyStops(any(), any(), any()) } returns stopFlow

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        // when
        locationFlow.emit(mockLocation)
        stopFlow.emit(networkResponse)
        val actualState = viewModel.state.drop(1).first()

        // then
        assertEquals(UIState.Success, actualState.currentState)
        assertEquals(expectedStopList.first().stop?.name, actualState.data.first().name)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}
