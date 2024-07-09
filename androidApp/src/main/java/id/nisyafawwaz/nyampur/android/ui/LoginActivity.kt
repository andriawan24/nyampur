package id.nisyafawwaz.nyampur.android.ui

import android.content.Context
import android.content.Intent
import android.util.Patterns
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityLoginBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.doAfterTextChanged
import id.nisyafawwaz.nyampur.android.utils.extensions.onClickThrottle
import id.nisyafawwaz.nyampur.android.utils.extensions.removeExtraPaddingError

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initIntent() = Unit

    override fun initViews() {
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
    }

    override fun initListener() {
        with(binding) {
            btnContinue.onClickThrottle {
                OtpActivity.start(this@LoginActivity)
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