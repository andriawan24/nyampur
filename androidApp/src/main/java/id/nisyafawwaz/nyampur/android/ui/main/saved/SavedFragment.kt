package id.nisyafawwaz.nyampur.android.ui.main.saved

import id.nisyafawwaz.nyampur.android.base.BaseFragment
import id.nisyafawwaz.nyampur.android.databinding.FragmentSavedBinding

class SavedFragment : BaseFragment<FragmentSavedBinding>() {

    override val binding: FragmentSavedBinding by lazy {
        FragmentSavedBinding.inflate(layoutInflater)
    }

    override fun initViews() = Unit
    override fun initProcess() = Unit
    override fun initActions() = Unit
    override fun initObservers() = Unit

    companion object {
        fun newInstance(): SavedFragment = SavedFragment()
    }
}