package com.nazar.petproject.data.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

private const val TIME_OUT = 10_000

private const val API_OPEN_METEO_HOST = "api.open-meteo.com"
private const val API_OPEN_METEO_PATH = "v1/forecast"
@Module
@InstallIn(SingletonComponent::class)
class HttpClientsModule {

    @Provides
    @Singleton
    fun provideOpenMeteoKtorClient() = HttpClient(Android) {

        defaultRequest {
            header(HttpHeaders.ContentType, ContentType.Application.Json)

            url {
                protocol = URLProtocol.HTTPS
                host = API_OPEN_METEO_HOST
                path(API_OPEN_METEO_PATH)
            }
        }

        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT

        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })

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
    }

}