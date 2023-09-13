package com.harmittaa.publictransitstops.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.harmittaa.publictransitstops.StopsByRadiusQuery
import kotlinx.coroutines.flow.flow

// simple structure to represent network result states.
// this would be more generic in a scenario where there's multiple different data sources
sealed interface NetworkResult {
    class Success(val data: List<StopsByRadiusQuery.Node>) : NetworkResult
    object NoLocationsNearby : NetworkResult
    object Error : NetworkResult
}

class StopsRepository(
    private val api: ApolloClient
) {
    private val searchRadius = 500

    fun getNearbyStops(lat: Double, lon: Double, radius: Int = searchRadius) =
        flow {
            val query = api.query(
                StopsByRadiusQuery(
                    lat = lat,
                    lon = lon,
                    radius = radius
                )
            )

            try {
                val networkResult =
                    query.execute().data?.stopsByRadius?.edges?.mapNotNull { it?.node }
                        ?: emptyList()

                if (networkResult.isEmpty()) {
                    emit(NetworkResult.NoLocationsNearby)
                } else {
                    emit(NetworkResult.Success(data = networkResult))
                }
            } catch (e: ApolloException) {
                println("Exception on fetching stops: $e")
                emit(NetworkResult.Error)
            }
        }
}
