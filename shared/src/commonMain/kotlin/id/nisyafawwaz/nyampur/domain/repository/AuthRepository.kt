package id.nisyafawwaz.nyampur.domain.repository

import io.github.jan.supabase.auth.user.UserInfo

interface AuthRepository {
    suspend fun sendEmailSignInOtp(email: String)
    suspend fun validateEmailOtp(token: String, email: String)
    suspend fun retrieveUserSession(): UserInfo?
    suspend fun signInWithGoogle(idToken: String)
    suspend fun signOut(): Boolean
}
