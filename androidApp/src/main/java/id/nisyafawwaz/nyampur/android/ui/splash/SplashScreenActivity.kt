package id.nisyafawwaz.nyampur.android.ui.splash

import android.annotation.SuppressLint
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivitySplashScreenBinding
import id.nisyafawwaz.nyampur.android.ui.authentication.LoginActivity
import id.nisyafawwaz.nyampur.android.ui.main.MainActivity
import id.nisyafawwaz.nyampur.android.utils.extensions.setStatusBarInset
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.ui.AuthVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    private val authVM: AuthVM by viewModel()

    override val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun initIntent() = Unit

    override fun initViews() {
        enableEdgeToEdge()
        binding.root.setStatusBarInset()
        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            authVM.retrieveUserSession()
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            authVM.retrieveUserSessionResult.collectLatest {
                when (it) {
                    is ResultState.Error -> {
                        LoginActivity.start(this@SplashScreenActivity)
                    }

                    is ResultState.Success -> {
                        if (it.data != null) {
                            MainActivity.start(this@SplashScreenActivity)
                        } else {
                            LoginActivity.start(this@SplashScreenActivity)
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun initListener() = Unit

    companion object {
        private const val SPLASH_DELAY = 2_000L
    }
}