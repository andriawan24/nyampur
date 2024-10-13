package id.nisyafawwaz.nyampur.data.remote.datasources.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import io.github.jan.supabase.gotrue.user.UserInfo

class SupabaseAuthDataSource(private val client: SupabaseClient) {

    suspend fun sendEmailSignInOtp(email: String) {
        client.auth.signInWith(provider = OTP) { this.email = email }
    }

    suspend fun validateEmailOtp(token: String, email: String) {
        client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, token)
    }

    suspend fun retrieveUserSession(): UserInfo? {
        val user = client.auth.sessionManager.loadSession()
        return user?.user
    }
}