package com.harmittaa.publictransitstops.ui.screen.nearestStops.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harmittaa.publictransitstops.R
import com.harmittaa.publictransitstops.ui.screen.nearestStops.NearestStopsScreenViewModel.NearestStopsState.UIState.Error
import com.harmittaa.publictransitstops.ui.theme.PublicTransitStopsTheme

@Composable
fun ErrorView(error: Error) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.error_view_generic_error_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "$error",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewErrorView() {
    PublicTransitStopsTheme {
        ErrorView(
            error = Error.Location
        )
    }
}
