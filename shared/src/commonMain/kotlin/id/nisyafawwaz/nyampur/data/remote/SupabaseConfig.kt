package id.nisyafawwaz.nyampur.data.remote

import id.nisyafawwaz.nyampur.BuildKonfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

object SupabaseConfig {
    fun getInstance(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BuildKonfig.SUPABASE_URL,
            supabaseKey = BuildKonfig.SUPABASE_API_KEY
        ) {
            install(Auth)
            install(Postgrest)
            defaultSerializer = KotlinXSerializer(
                Json {
                    ignoreUnknownKeys = true
                }
            )
            requestTimeout = 60.seconds
        }
    }
}