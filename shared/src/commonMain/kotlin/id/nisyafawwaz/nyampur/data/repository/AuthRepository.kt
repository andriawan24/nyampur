package id.nisyafawwaz.nyampur.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.OTP

class AuthRepository(private val client: SupabaseClient) {
    suspend fun signInWithOtp(email: String) {
        try {
            client.auth.signInWith(provider = OTP) {
                this.email = email
            }
        } catch (e: Exception) {
            print("Sign in error: " + e.message.orEmpty())
        }
    }
}