package id.nisyafawwaz.nyampur.data.repository

import id.nisyafawwaz.nyampur.data.remote.SupabaseDataSource
import id.nisyafawwaz.nyampur.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.serialization.json.JsonObject

class AuthRepositoryImpl(private val supabaseDataSource: SupabaseDataSource) : AuthRepository {

    override suspend fun sendEmailSignInOtp(email: String) {
        supabaseDataSource.sendEmailSignInOtp(email)
    }

    override suspend fun validateEmailOtp(token: String, email: String) {
        supabaseDataSource.validateEmailOtp(token, email)
    }

    override suspend fun retrieveUserSession(): UserInfo? {
        return supabaseDataSource.retrieveUserSession()
    }
}