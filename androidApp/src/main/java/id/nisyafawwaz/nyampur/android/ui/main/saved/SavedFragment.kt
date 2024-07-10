package id.nisyafawwaz.nyampur.android.ui.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.nisyafawwaz.nyampur.android.databinding.FragmentHomeBinding
import id.nisyafawwaz.nyampur.android.databinding.FragmentSavedBinding

class SavedFragment : Fragment() {

    private var hasInitialized = false

    private val binding: FragmentSavedBinding by lazy {
        FragmentSavedBinding.inflate(layoutInflater)
    }

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
            initViews()
            hasInitialized = true
        }
    }

    private fun initViews() {

    }

    override fun onStop() {
        super.onStop()
        hasInitialized = false
    }

    companion object {
        fun newInstance(): SavedFragment {
            return SavedFragment()
        }
    }
}