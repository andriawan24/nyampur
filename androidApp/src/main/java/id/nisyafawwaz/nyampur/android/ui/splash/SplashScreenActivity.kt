package id.nisyafawwaz.nyampur.android.ui.splash

import android.annotation.SuppressLint
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivitySplashScreenBinding
import id.nisyafawwaz.nyampur.android.ui.authentication.LoginActivity
import id.nisyafawwaz.nyampur.android.ui.main.MainActivity
import id.nisyafawwaz.nyampur.android.utils.extensions.setStatusBarInset
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    override val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun initIntent() = Unit

    override fun initViews() {
        enableEdgeToEdge()
        binding.root.setStatusBarInset()
        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            MainActivity.start(this@SplashScreenActivity)
        }
    }

    override fun initListener() = Unit

    companion object {
        private const val SPLASH_DELAY = 2_000L
    }
}