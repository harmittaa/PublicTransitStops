package com.harmittaa.publictransitstops.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.harmittaa.publictransitstops.StopsByRadiusQuery
import kotlinx.coroutines.flow.flow

sealed interface NetworkRequestState {
    class SUCCESS(val data: List<StopsByRadiusQuery.Node>) : NetworkRequestState
    class NO_LOCATIONS_NEARBY() : NetworkRequestState
    object ERROR : NetworkRequestState
}
class StopsRepository(
    private val api: ApolloClient
) {
    private val searchRadius = 500

    fun getNearbyStops(lat: Double, lon: Double, radius: Int = searchRadius) =
        flow<NetworkRequestState> {
            val query = api.query(
                StopsByRadiusQuery(
                    lat = lat,
                    lon = lon,
                    radius = radius
                )
            )

            try {
                val networkResult: List<StopsByRadiusQuery.Edge> =
                    query.execute().data?.stopsByRadius?.edges?.filterNotNull()?.map { it }
                        ?: emptyList()
                if (networkResult.isEmpty()) {
                    emit(NetworkRequestState.NO_LOCATIONS_NEARBY())
                } else {
                    emit(NetworkRequestState.SUCCESS(data = networkResult.mapNotNull { it.node }))
                }
            } catch (e: ApolloException) {
                println("Exception on fetching stops: $e")
                emit(NetworkRequestState.ERROR)
            }
        }
}
