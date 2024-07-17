package id.nisyafawwaz.nyampur.android.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.nisyafawwaz.nyampur.android.databinding.FragmentHomeBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.observeLiveData
import id.nisyafawwaz.nyampur.ui.RecipeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val recipeViewModel: RecipeViewModel by viewModel()

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
        initObserver()
        recipeViewModel.getRecipes("sarapan", 1)
    }

    private fun initObserver() {
        recipeViewModel.getRecipesResult.observeLiveData(
            requireActivity(),
            onLoading = {

            },
            onSuccess = { recipes ->
                Log.d(HomeFragment::class.simpleName, "initObserver: $recipes")
            },
            onFailure = {

            }
        )
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