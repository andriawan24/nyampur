package id.nisyafawwaz.nyampur.android.ui.authentication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityLoginBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.doAfterTextChanged
import id.nisyafawwaz.nyampur.android.utils.extensions.enable
import id.nisyafawwaz.nyampur.android.utils.extensions.getValue
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.extensions.removeExtraPaddingError
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.ui.AuthenticationViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
            btnContinue.onClick {
                authenticationViewModel.signInOtp(etEmail.getValue())
            }
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            authenticationViewModel.signInOtpResult.collectLatest {
                when (it) {
                    ResultState.Loading -> {
                        binding.btnContinue.isEnabled = false
                    }

                    is ResultState.Error -> {
                        Log.e(LoginActivity::class.simpleName, it.error.message.orEmpty(), it.error)
                        Toast.makeText(
                            this@LoginActivity,
                            it.error.message.orEmpty(),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.btnContinue.isEnabled = true
                    }

                    is ResultState.Success -> {
                        OtpActivity.start(this@LoginActivity, binding.etEmail.getValue())
                        binding.btnContinue.isEnabled = true
                    }

                    else -> {
                        // Do nothing
                    }
                }
            }
        }
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