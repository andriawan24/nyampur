package id.nisyafawwaz.nyampur.data.repository

import id.nisyafawwaz.nyampur.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.serialization.json.JsonObject

class AuthRepositoryImpl(private val client: SupabaseClient) : AuthRepository {

    override suspend fun sendEmailSignInOtp(email: String) {
        client.auth.signInWith(provider = OTP) { this.email = email }
    }

    override suspend fun validateEmailOtp(token: String, email: String) {
        client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, token)
    }

    override suspend fun retrieveUserSession(): UserInfo? {
        val user = client.auth.sessionManager.loadSession()
        return user?.user
    }
}