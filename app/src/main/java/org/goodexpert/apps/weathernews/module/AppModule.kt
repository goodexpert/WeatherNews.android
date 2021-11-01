package org.goodexpert.apps.weathernews.module

import android.util.Log
import com.github.zsoltk.compose.backpress.BackPressHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.goodexpert.apps.weathernews.network.ApiClient

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val TIME_OUT = 60_000

    @Provides
    fun provideBackPressHandler(): BackPressHandler {
        return BackPressHandler()
    }

    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })

                engine {
                    connectTimeout = TIME_OUT
                    socketTimeout = TIME_OUT
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }

                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    @Provides
    fun provideApiClient(httpClient: HttpClient): ApiClient {
        return ApiClient(httpClient)
    }
}