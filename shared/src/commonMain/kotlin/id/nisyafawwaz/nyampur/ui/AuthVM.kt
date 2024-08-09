package id.nisyafawwaz.nyampur.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.usecases.auth.RetrieveUserSessionUseCase
import id.nisyafawwaz.nyampur.domain.usecases.auth.SendEmailOtpUseCase
import id.nisyafawwaz.nyampur.domain.usecases.auth.ValidateEmailOtpUseCase
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthVM : ViewModel(), KoinComponent {

    private val sendEmailOtpUseCase: SendEmailOtpUseCase by inject()
    private val validateEmailOtpUseCase: ValidateEmailOtpUseCase by inject()
    private val retrieveUserSessionUseCase: RetrieveUserSessionUseCase by inject()

    private val _signInOtpResult = MutableStateFlow<ResultState<Boolean>>(ResultState.Idle)
    val signInOtpResult = _signInOtpResult.asStateFlow()

    private val _validateEmailOtpResult = MutableStateFlow<ResultState<Boolean>>(ResultState.Idle)
    val validateEmailOtpResult = _validateEmailOtpResult.asStateFlow()

    private val _retrieveUserSessionResult = MutableStateFlow<ResultState<UserInfo?>>(ResultState.Idle)
    val retrieveUserSessionResult = _retrieveUserSessionResult.asStateFlow()

    fun signInOtp(email: String) {
        viewModelScope.launch {
            sendEmailOtpUseCase.execute(email).collectLatest {
                _signInOtpResult.emit(it)
            }
        }
    }

    fun validateEmailOtp(token: String, email: String) {
        viewModelScope.launch {
            validateEmailOtpUseCase.execute(token = token, email = email).collectLatest {
                _validateEmailOtpResult.emit(it)
            }
        }
    }

    fun retrieveUserSession() {
        viewModelScope.launch {
            retrieveUserSessionUseCase.execute().collectLatest {
                _retrieveUserSessionResult.emit(it)
            }
        }
    }
}