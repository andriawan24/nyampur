package id.nisyafawwaz.nyampur.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    abstract val binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initIntent()
        initViews()
        initListener()
        initObserver()
    }

    abstract fun initIntent()
    abstract fun initViews()
    abstract fun initListener()
    protected fun initObserver() = Unit
}