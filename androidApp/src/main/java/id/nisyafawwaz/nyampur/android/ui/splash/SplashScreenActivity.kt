package id.nisyafawwaz.nyampur.android.ui.splash

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivitySplashScreenBinding
import id.nisyafawwaz.nyampur.android.ui.authentication.LoginActivity
import id.nisyafawwaz.nyampur.android.ui.main.MainActivity
import id.nisyafawwaz.nyampur.android.utils.extensions.setStatusBarInset
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.ui.AuthenticationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    private val authenticationViewModel: AuthenticationViewModel by viewModel()

    override val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun initIntent() = Unit

    override fun initViews() {
        enableEdgeToEdge(
            navigationBarStyle =
                SystemBarStyle.light(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT,
                ),
        )
        binding.root.setStatusBarInset()
        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            authenticationViewModel.retrieveUserSession()
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            authenticationViewModel.retrieveUserSessionResult.collectLatest {
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
