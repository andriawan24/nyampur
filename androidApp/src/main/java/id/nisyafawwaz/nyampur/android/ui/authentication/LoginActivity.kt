package id.nisyafawwaz.nyampur.android.ui.authentication

import SupabaseUtil
import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityLoginBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.doAfterTextChanged
import id.nisyafawwaz.nyampur.android.utils.extensions.getValue
import id.nisyafawwaz.nyampur.android.utils.extensions.onClickThrottle
import id.nisyafawwaz.nyampur.android.utils.extensions.removeExtraPaddingError
import id.nisyafawwaz.nyampur.data.repository.AuthRepository
import id.nisyafawwaz.nyampur.domain.usecases.SignInOtpUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val signInOtpResult = MutableSharedFlow<Boolean>()
    private var signInOtpUseCase: SignInOtpUseCase? = null

    override fun initIntent() = Unit

    override fun initViews() {
        val client = SupabaseUtil.getClient()
        val repository = AuthRepository(client)
        signInOtpUseCase = SignInOtpUseCase(repository)

        binding.apply {
            tilEmail.removeExtraPaddingError()
            etEmail.doAfterTextChanged {
                if (it.isBlank()) {
                    tilEmail.apply {
                        isErrorEnabled = true
                        error = context.getString(R.string.error_email_empty)
                    }
                    btnContinue.isEnabled = false
                } else if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                    tilEmail.apply {
                        isErrorEnabled = true
                        error = context.getString(R.string.error_email_invalid)
                    }
                    btnContinue.isEnabled = false
                } else {
                    tilEmail.apply {
                        isErrorEnabled = false
                        error = null
                    }
                    btnContinue.isEnabled = true
                }
            }
        }

        lifecycleScope.launch {
            signInOtpResult.collectLatest {
                if (it) {
                    OtpActivity.start(this@LoginActivity, binding.etEmail.getValue())
                } else {
                    Toast.makeText(this@LoginActivity, "OTP Failed to sent", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initListener() {
        with(binding) {
            btnContinue.onClickThrottle {
                sendOtpEmail()
            }
        }
    }

    private fun sendOtpEmail() {
        lifecycleScope.launch(Dispatchers.IO) {
            signInOtpUseCase?.execute(binding.etEmail.getValue())?.collectLatest {
                signInOtpResult.emit(it)
            }
        }
    }

    companion object {
        fun start(context: Context) {
            Intent(context, LoginActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}