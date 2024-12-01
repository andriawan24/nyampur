package id.nisyafawwaz.nyampur.domain.repository

import io.github.jan.supabase.gotrue.user.UserInfo

interface AuthRepository {
    suspend fun sendEmailSignInOtp(email: String)
    suspend fun validateEmailOtp(token: String, email: String)
    suspend fun retrieveUserSession(): UserInfo?
    suspend fun signOut(): Boolean
}
