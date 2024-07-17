package id.nisyafawwaz.nyampur

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

actual val httpClient: HttpClient
    get() = HttpClient(Darwin) {
        install(ContentNegotiation) {
            json()
        }
    }