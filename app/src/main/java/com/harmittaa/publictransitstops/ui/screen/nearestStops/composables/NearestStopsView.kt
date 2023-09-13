package com.harmittaa.publictransitstops.ui.screen.nearestStops.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harmittaa.publictransitstops.model.Stop
import com.harmittaa.publictransitstops.ui.theme.PublicTransitStopsTheme

@Composable
fun NearestStopsView(data: List<Stop>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        item {
            Text(
                text = "Here are the closest ${data.size} stops based on your location",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        items(data) { stop ->
            NearestStopListItem(
                name = stop.name,
                distance = stop.distance,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
        }
    }
}

@Composable
fun NearestStopListItem(
    name: String,
    distance: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(70.dp)
        )
    ) {
        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(.8f)
        )
        Text(
            text = "${distance}m",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun PreviewNearestStopsView() {
    PublicTransitStopsTheme {
        val mockData = mutableListOf<Stop>()
        repeat(5) {
            mockData.add(
                Stop(
                    name = "This here would be a very, very, very, very, very long name",
                    distance = it * 100
                )
            )
        }
        NearestStopsView(
            data = mockData
        )
    }
}
