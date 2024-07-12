package id.nisyafawwaz.nyampur.domain.usecases

import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.repository.AuthRepository
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RetrieveUserSessionUseCase(private val authRepository: AuthRepository) {

    fun execute(): Flow<ResultState<UserInfo?>> = flow {
        emit(ResultState.Loading)
        try {
            val user = authRepository.retrieveUserSession()
            emit(ResultState.Success(user))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }
}