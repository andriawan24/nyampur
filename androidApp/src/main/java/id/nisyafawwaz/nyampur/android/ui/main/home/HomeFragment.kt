package id.nisyafawwaz.nyampur.android.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.nisyafawwaz.nyampur.android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var hasInitialized = false

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
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
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}