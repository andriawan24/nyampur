package id.nisyafawwaz.nyampur.android.ui.main.home

import android.util.Log
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
import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import id.nisyafawwaz.nyampur.ui.AccountManager
import id.nisyafawwaz.nyampur.ui.AuthVM
import id.nisyafawwaz.nyampur.ui.RecipeVM
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val recipeVM: RecipeVM by viewModel()
    private val accountManager: AccountManager by inject()

    private val quickMealAdapter = QuickMealAdapter {
        recipeVM.saveRecipes(
            RecipeResponse(
                imageUrl = it.imageUrl,
                cookTime = it.cookTime,
                title = it.title,
                level = it.level,
                usersId = accountManager.getCurrentUser()?.id.orEmpty()
            )
        )
    }

    override val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        initRecycler()
        binding.root.setOnRefreshListener {
            binding.root.isRefreshing = true
            initProcess()
        }
    }

    private fun initRecycler() {
        with(binding.rvQuickMeals) {
            adapter = quickMealAdapter
            layoutManager = GridLayoutManager(requireActivity(), 2)
            addItemDecoration(GridItemDecoration(2))
        }
    }

    override fun initProcess() {
        recipeVM.getRecipes(type = DEFAULT_TYPE, userId = accountManager.getCurrentUser()?.id.orEmpty(), page = 1)
    }

    override fun initObservers() {
        observerRecipeResult()
        observerSaveRecipeResult()
    }

    private fun observerSaveRecipeResult() {
        recipeVM.saveRecipesResult.observeLiveData(
            requireActivity(),
            onSuccess = {
                // TODO: Immediately changed the saved recipes
            },
            onFailure = {
                // TODO: Show error when save recipe
            }
        )
    }

    private fun observerRecipeResult() = with(binding) {
        recipeVM.getRecipesResult.observeLiveData(
            requireActivity(),
            onLoading = {
                msvQuickMeals.showLoading()
            },
            onEmpty = {
                root.isRefreshing = false
                msvQuickMeals.showEmpty()
            },
            onSuccess = { recipes ->
                root.isRefreshing = false
                quickMealAdapter.addAll(recipes)
                msvQuickMeals.showDefault()
            },
            onFailure = {
                root.isRefreshing = false
                msvQuickMeals.showError()
            }
        )
    }

    companion object {
        private const val DEFAULT_TYPE = "sarapan"

        fun newInstance(): HomeFragment = HomeFragment()
    }
}