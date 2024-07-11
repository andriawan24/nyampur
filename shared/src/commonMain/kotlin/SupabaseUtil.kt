import id.nisyafawwaz.nyampur.BuildKonfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

object SupabaseUtil {
    fun getClient(): SupabaseClient {
        return createSupabaseClient(
            BuildKonfig.SUPABASE_URL,
            BuildKonfig.SUPABASE_API_KEY
        ) {
            install(Auth)
            install(Postgrest)
            defaultSerializer = KotlinXSerializer(Json)
        }
    }
}