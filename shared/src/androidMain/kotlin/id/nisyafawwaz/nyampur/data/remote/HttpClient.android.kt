package id.nisyafawwaz.nyampur.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json

actual val httpClient: HttpClient
    get() = HttpClient(OkHttp) {
        defaultRequest {
            host = "52.23.207.131"
            url {
                protocol = URLProtocol.HTTP
            }
        }

        install(Resources)

        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
            connectTimeoutMillis = 60_000
            socketTimeoutMillis = 60_000
        }

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.BODY
            sanitizeHeader { header ->
                header == HttpHeaders.Authorization
            }
        }

        install(ContentNegotiation) {
            json()
        }

        install(Auth) {
            basic {
                credentials { BasicAuthCredentials("admin", "admin123") }
            }
        }
    }
