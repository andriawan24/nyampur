package id.nisyafawwaz.nyampur.android.ui.main

import android.content.Context
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityMainBinding
import id.nisyafawwaz.nyampur.android.ui.camera.CameraActivity
import id.nisyafawwaz.nyampur.android.ui.main.home.HomeFragment
import id.nisyafawwaz.nyampur.android.ui.main.saved.SavedFragment
import id.nisyafawwaz.nyampur.android.utils.extensions.getTakePhotoDirectory
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.extensions.showFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initIntent() = Unit

    override fun initViews() {
        setupStatusBar()
        initDefaultHomeMenu()
        cleanUpImages()
    }

    private fun cleanUpImages() {
        lifecycleScope.launch(Dispatchers.IO) {
            getTakePhotoDirectory().deleteRecursively()
        }
    }

    private fun initDefaultHomeMenu() {
        supportFragmentManager.showFragment(
            fragment = HomeFragment.newInstance(),
            intoLayoutId = binding.flFragment.id,
        )
    }

    private fun setupStatusBar() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }

    override fun initListener() {
        binding.bottomNavMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.showFragment(
                        HomeFragment.newInstance(),
                        binding.flFragment.id,
                    )
                    true
                }

                R.id.menu_save -> {
                    supportFragmentManager.showFragment(
                        SavedFragment.newInstance(),
                        binding.flFragment.id,
                    )
                    true
                }

                else -> false
            }
        }

        binding.btnTakePicture.onClick {
            CameraActivity.start(this)
        }
    }

    companion object {
        fun start(
            context: Context,
            clearTask: Boolean = true,
        ) {
            val intent =
                Intent(context, MainActivity::class.java).apply {
                    if (clearTask) {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                }
            context.startActivity(intent)
        }
    }
}
