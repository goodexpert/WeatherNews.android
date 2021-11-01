package org.goodexpert.apps.weathernews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zsoltk.compose.backpress.BackPressHandler
import dagger.hilt.android.AndroidEntryPoint
import org.goodexpert.apps.weathernews.model.WeatherInfo
import org.goodexpert.apps.weathernews.network.ApiClient
import org.goodexpert.apps.weathernews.ui.theme.AppLocalProvider
import org.goodexpert.apps.weathernews.ui.theme.LocalApiClient
import org.goodexpert.apps.weathernews.ui.theme.WeatherNewsTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var apiClient: ApiClient
    @Inject lateinit var backPressHandler: BackPressHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppLocalProvider(
                apiClient = apiClient,
                backPressHandler = backPressHandler
            ) {
                WeatherNewsTheme {
                    val cities = arrayOf("Auckland", "Dublin", "Wellington")

                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(cities) { city ->
                                Weather(city)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!backPressHandler.handle()) {
            super.onBackPressed()
        }
    }
}

@Composable
fun Weather(city: String) {
    val apiClient = LocalApiClient.current
    var weatherInfo by remember { mutableStateOf<WeatherInfo?>(null) }

    LaunchedEffect(Unit) {
        weatherInfo = apiClient.getWeatherInfo(city)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (weatherInfo == null) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.subtitle1
            )
        }

        weatherInfo?.let { weatherInfo ->
            Text(
                text = "Name : ${weatherInfo.name}",
                style = MaterialTheme.typography.subtitle1
            )

            Text(
                text = "Temp : ${weatherInfo.main.temp}",
                style = MaterialTheme.typography.body1
            )

            Text(
                text = "Temp (max) : ${weatherInfo.main.tempMax}",
                style = MaterialTheme.typography.body1
            )

            Text(
                text = "Temp (min) : ${weatherInfo.main.tempMin}",
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherNewsTheme {
        Weather("Android")
    }
}