package org.goodexpert.apps.weathernews.model

import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    val type: Int,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)