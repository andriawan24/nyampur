package id.nisyafawwaz.nyampur.android.ui

import android.content.Context
import android.content.Intent
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityLoginBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.onClickThrottle

class LoginActivity: BaseActivity<ActivityLoginBinding>() {

    override val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initIntent() = Unit
    override fun initViews() = Unit

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