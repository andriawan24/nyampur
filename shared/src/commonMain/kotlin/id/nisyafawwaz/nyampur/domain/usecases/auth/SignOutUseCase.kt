package id.nisyafawwaz.nyampur.domain.usecases.auth

import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignOutUseCase(private val authRepository: AuthRepository) {
    fun execute(): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading)
        try {
            val user = authRepository.signOut()
            emit(ResultState.Success(user))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }
}
