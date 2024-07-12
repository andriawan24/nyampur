package id.nisyafawwaz.nyampur.di

import id.nisyafawwaz.nyampur.BuildKonfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        createSupabaseClient(
            BuildKonfig.SUPABASE_URL,
            BuildKonfig.SUPABASE_API_KEY
        ) {
            install(Auth)
            install(Postgrest)
            defaultSerializer = KotlinXSerializer(Json)
        }
    }
}