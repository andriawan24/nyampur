package id.nisyafawwaz.nyampur.domain.usecases

import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ValidateEmailOtpUseCase(private val authRepository: AuthRepository) {

    fun execute(token: String, email: String): Flow<ResultState<Boolean>> = flow {
        emit(ResultState.Loading)
        try {
            authRepository.validateEmailOtp(token = token, email = email)
            emit(ResultState.Success(true))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }
}