package id.nisyafawwaz.nyampur.android.ui.main.home

import androidx.recyclerview.widget.GridLayoutManager
import id.nisyafawwaz.nyampur.android.adapters.QuickMealAdapter
import id.nisyafawwaz.nyampur.android.base.BaseFragment
import id.nisyafawwaz.nyampur.android.databinding.FragmentHomeBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.observeLiveData
import id.nisyafawwaz.nyampur.android.utils.extensions.showDefault
import id.nisyafawwaz.nyampur.android.utils.extensions.showEmpty
import id.nisyafawwaz.nyampur.android.utils.extensions.showError
import id.nisyafawwaz.nyampur.android.utils.extensions.showLoading
import id.nisyafawwaz.nyampur.android.utils.list.GridItemDecoration
import id.nisyafawwaz.nyampur.android.utils.views.MultiStateView
import id.nisyafawwaz.nyampur.ui.RecipeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val recipeViewModel: RecipeViewModel by viewModel()

    private val quickMealAdapter = QuickMealAdapter()

    override val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        initRecycler()
    }

    private fun initRecycler() {
        with(binding.rvQuickMeals) {
            adapter = quickMealAdapter
            layoutManager = GridLayoutManager(requireActivity(), 2)
            addItemDecoration(GridItemDecoration(2))
        }
    }

    override fun initProcess() {
        recipeViewModel.getRecipes("sarapan", 1)
    }

    override fun initObservers() {
        recipeViewModel.getRecipesResult.observeLiveData(
            requireActivity(),
            onLoading = {
                binding.msvQuickMeals.showLoading()
            },
            onEmpty = {
                binding.msvQuickMeals.showEmpty()
            },
            onSuccess = { recipes ->
                quickMealAdapter.addAll(recipes)
                binding.msvQuickMeals.showDefault()
            },
            onFailure = {
                binding.msvQuickMeals.showError()
            }
        )
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}