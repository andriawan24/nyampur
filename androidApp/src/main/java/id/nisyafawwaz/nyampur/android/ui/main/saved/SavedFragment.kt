package id.nisyafawwaz.nyampur.android.ui.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.nisyafawwaz.nyampur.android.base.BaseFragment
import id.nisyafawwaz.nyampur.android.databinding.FragmentHomeBinding
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