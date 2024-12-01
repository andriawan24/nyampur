package id.nisyafawwaz.nyampur.data.remote.datasources.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.user.UserInfo

class SupabaseAuthDataSource(private val client: SupabaseClient) {

    suspend fun sendEmailSignInOtp(email: String) {
        client.auth.signInWith(provider = OTP) { this.email = email }
    }

    suspend fun validateEmailOtp(token: String, email: String) {
        client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, token)
    }

    suspend fun signInWithGoogle(token: String) {
        client.auth.signInWith(IDToken) {
            idToken = token
            provider = Google
        }
    }

    suspend fun retrieveUserSession(): UserInfo? {
        val user = client.auth.sessionManager.loadSession()
        return user?.user
    }

    suspend fun signOut() {
        client.auth.signOut()
    }
}
