package com.harmittaa.publictransitstops.extensions

import com.harmittaa.publictransitstops.StopsByRadiusQuery
import com.harmittaa.publictransitstops.model.Stop

fun StopsByRadiusQuery.Node.toLocalDataOrNull(): Stop? {
    if (this.stop == null || this.distance == null) return null
    return Stop(name = this.stop.name, distance = this.distance)
}
