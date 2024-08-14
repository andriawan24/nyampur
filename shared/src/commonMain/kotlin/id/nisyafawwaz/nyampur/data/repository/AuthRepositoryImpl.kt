package id.nisyafawwaz.nyampur.data.repository

import id.nisyafawwaz.nyampur.data.remote.datasources.SupabaseDataSource
import id.nisyafawwaz.nyampur.domain.repository.AuthRepository
import io.github.jan.supabase.gotrue.user.UserInfo

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