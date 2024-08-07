package id.nisyafawwaz.nyampur.android.ui.authentication

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityLoginBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.doAfterTextChanged
import id.nisyafawwaz.nyampur.android.utils.extensions.enable
import id.nisyafawwaz.nyampur.android.utils.extensions.getValue
import id.nisyafawwaz.nyampur.android.utils.extensions.observeLiveData
import id.nisyafawwaz.nyampur.android.utils.extensions.onClickWithThrottle
import id.nisyafawwaz.nyampur.android.utils.extensions.removeExtraPaddingError
import id.nisyafawwaz.nyampur.ui.AuthenticationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val authenticationViewModel: AuthenticationViewModel by viewModel()

    override val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        binding.apply {
            tilEmail.removeExtraPaddingError()
            etEmail.doAfterTextChanged {
                when {
                    it.isBlank() -> {
                        tilEmail.apply {
                            isErrorEnabled = true
                            error = context.getString(R.string.error_email_empty)
                        }
                        btnContinue.isEnabled = false
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(it).matches() -> {
                        tilEmail.apply {
                            isErrorEnabled = true
                            error = context.getString(R.string.error_email_invalid)
                        }
                        btnContinue.isEnabled = false
                    }

                    else -> {
                        tilEmail.apply {
                            isErrorEnabled = false
                            error = null
                        }
                        btnContinue.isEnabled = true
                    }
                }
            }
        }
    }

    override fun initListener() {
        with(binding) {
            btnContinue.onClickWithThrottle {
                authenticationViewModel.signInOtp(etEmail.getValue())
            }
        }
    }

    override fun initObserver() {
        authenticationViewModel.signInOtpResult.observeLiveData(
            this,
            onLoading = {
                // TODO: Handle Loading State
            },
            onSuccess = {
                OtpActivity.start(this@LoginActivity, binding.etEmail.getValue())
            },
            onFailure = {
                Toast.makeText(
                    this@LoginActivity,
                    it.message.orEmpty(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    override fun onResume() {
        super.onResume()
        binding.btnContinue.enable()
    }

    companion object {
        fun start(context: Context) {
            Intent(context, LoginActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}