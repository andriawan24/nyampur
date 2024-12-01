package id.nisyafawwaz.nyampur.domain.usecases.auth

import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignInWithGoogleUseCase(private val authRepository: AuthRepository) {

    fun execute(idToken: String): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading)
        try {
            authRepository.signInWithGoogle(idToken)
            emit(ResultState.Success(true))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }
}
