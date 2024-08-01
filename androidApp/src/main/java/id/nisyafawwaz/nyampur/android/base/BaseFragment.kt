package id.nisyafawwaz.nyampur.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding>: Fragment() {

    abstract val binding: VB
    private var hasInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInitialized) {
            initObservers()
            initViews()
            initProcess()
            initActions()

            hasInitialized = true
        }
    }

    override fun onStop() {
        super.onStop()
        hasInitialized = false
    }

    abstract fun initViews()
    abstract fun initProcess()
    abstract fun initActions()
    abstract fun initObservers()
}