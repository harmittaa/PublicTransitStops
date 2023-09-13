package com.harmittaa.publictransitstops.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apollographql.apollo3.ApolloClient
import com.harmittaa.publictransitstops.StopsByRadiusQuery
import com.harmittaa.publictransitstops.ui.screen.LocationPermissionScreen
import com.harmittaa.publictransitstops.ui.theme.PublicTransitStopsTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val apolloClient: ApolloClient by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*MainScope().launch {
            val response =
                apolloClient.query(StopsByRadiusQuery(lat = 60.199, lon = 24.938, radius = 200))
                    .execute()
            println("Name = ${response.data?.stopsByRadius?.edges?.first()?.node?.distance}")

         */
        setContent {
            PublicTransitStopsApp()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PublicTransitStopsTheme {
        Greeting("Android")
    }
}
