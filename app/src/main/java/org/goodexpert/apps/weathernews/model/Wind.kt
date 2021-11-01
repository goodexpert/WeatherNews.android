package org.goodexpert.apps.weathernews.model

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val speed: Double,
    val deg: Int
)
