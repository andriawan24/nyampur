package id.nisyafawwaz.nyampur.data.repository

import id.nisyafawwaz.nyampur.data.remote.datasources.supabase.SupabaseAuthDataSource
import id.nisyafawwaz.nyampur.domain.repository.AuthRepository
import io.github.jan.supabase.gotrue.user.UserInfo

class AuthRepositoryImpl(private val supabaseRecipeDataSource: SupabaseAuthDataSource) : AuthRepository {

    override suspend fun sendEmailSignInOtp(email: String) {
        supabaseRecipeDataSource.sendEmailSignInOtp(email)
    }

    override suspend fun validateEmailOtp(token: String, email: String) {
        supabaseRecipeDataSource.validateEmailOtp(token, email)
    }

    override suspend fun retrieveUserSession(): UserInfo? {
        return supabaseRecipeDataSource.retrieveUserSession()
    }

    override suspend fun signOut(): Boolean {
        supabaseRecipeDataSource.signOut()
        return true
    }
}
