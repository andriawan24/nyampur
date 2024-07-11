package id.nisyafawwaz.nyampur.domain.usecases

import id.nisyafawwaz.nyampur.data.repository.AuthRepository
import kotlinx.coroutines.flow.flow

class SignInOtpUseCase(private val authRepository: AuthRepository) {

    fun execute(email: String) = flow {
        try {
            authRepository.signInWithOtp(email)
            emit(true)
        } catch (_: Exception) {
            emit(false)
        }
    }
}