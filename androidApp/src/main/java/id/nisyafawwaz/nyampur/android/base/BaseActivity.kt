package id.nisyafawwaz.nyampur.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Used as base activity for all activities in this project,
 * containing functions to reduce boilerplate to init an activity
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    /**
     * Ensure all activities use [ViewBinding] instance
     */
    abstract val binding: VB

    /**
     * Override [onCreate] so it can called init function sequentially
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initIntent()
        initViews()
        initListener()
        initObserver()
    }

    protected open fun initIntent() = Unit

    /**
     * Initialize views and their initial states.
     * Called after [initIntent] and before [initListener].
     */
    abstract fun initViews()

    /**
     * Set up view listeners and handle user interactions.
     * Called after [initViews] and before [initObserver].
     */
    abstract fun initListener()

    /**
     * Set up LiveData/Flow observers and handle data updates.
     * Called after [initListener] as the final initialization step.
     */
    protected open fun initObserver() = Unit
}
