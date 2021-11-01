package org.goodexpert.apps.weathernews.model

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val id: String,
    val main: String,
    val description: String,
    val icon: String
)