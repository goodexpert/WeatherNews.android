package org.goodexpert.apps.weathernews.network

import io.ktor.client.*
import io.ktor.client.request.*
import org.goodexpert.apps.weathernews.model.WeatherInfo

class ApiClient(private val httpClient: HttpClient) {
    companion object {
        private const val KEY = "openweathermap-api-key"
        private const val URI = "https://api.openweathermap.org/data/2.5/weather"
    }

    suspend fun getWeatherInfo(cityName: String): WeatherInfo = httpClient.get(URI) {
        parameter("appid", KEY)
        parameter("q", cityName)
    }
}