package org.goodexpert.apps.weathernews.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfo(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Int
)