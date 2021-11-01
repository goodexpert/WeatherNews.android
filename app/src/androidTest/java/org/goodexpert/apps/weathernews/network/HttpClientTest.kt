package org.goodexpert.apps.weathernews.network

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import org.goodexpert.apps.weathernews.model.Coord
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HttpClientTest {

    private lateinit var engine: HttpClientEngine
    private lateinit var httpClient: HttpClient
    private lateinit var apiClient: ApiClient

    private val content = """
        {
            "coord":{"lon":174.7667,"lat":-36.8667},
            "weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02d"}],
            "base":"stations",
            "main":{"temp":290.76,"feels_like":290.14,"temp_min":289.33,"temp_max":291.36,"pressure":1016,"humidity":60},
            "visibility":10000,
            "wind":{"speed":4.47,"deg":217,"gust":6.71},
            "clouds":{"all":20},
            "dt":1635738254,
            "sys":{"type":2,"id":2012285,"country":"NZ","sunrise":1635700553,"sunset":1635749581},
            "timezone":46800,
            "id":2193733,
            "name":"Auckland",
            "cod":200
        }
        """.trimIndent()

    @Before
    fun setup() {
        engine = MockEngine { request ->
            respond(
                content = ByteReadChannel(content),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        httpClient = HttpClient(engine) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
        apiClient = ApiClient(httpClient)
    }

    @Test
    fun testGetWeatherInfo() {
        runBlocking {
            val response = apiClient.getWeatherInfo("Auckland")
            Assert.assertEquals("Auckland", response.name)
            Assert.assertEquals(200, response.cod)
            Assert.assertEquals(Coord(174.7667, -36.8667), response.coord)
        }
    }
}