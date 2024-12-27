package id.nisyafawwaz.nyampur.android.ui.main.home

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.adapters.QuickMealAdapter
import id.nisyafawwaz.nyampur.android.base.BaseFragment
import id.nisyafawwaz.nyampur.android.databinding.FragmentHomeBinding
import id.nisyafawwaz.nyampur.android.ui.camera.CameraActivity
import id.nisyafawwaz.nyampur.android.ui.profile.ProfileActivity
import id.nisyafawwaz.nyampur.android.utils.extensions.observeLiveData
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.extensions.showDefault
import id.nisyafawwaz.nyampur.android.utils.extensions.showEmpty
import id.nisyafawwaz.nyampur.android.utils.extensions.showError
import id.nisyafawwaz.nyampur.android.utils.extensions.showLoading
import id.nisyafawwaz.nyampur.android.utils.list.GridItemDecoration
import id.nisyafawwaz.nyampur.ui.AccountManager
import id.nisyafawwaz.nyampur.ui.RecipeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val recipeViewModel: RecipeViewModel by viewModel()
    private val accountManager: AccountManager by inject()

    private val quickMealAdapter =
        QuickMealAdapter {
            recipeViewModel.run {
                if (it.isSaved) deleteSavedRecipe(it) else saveRecipe(it)
            }
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
        recipeViewModel.getRecipes(
            type = DEFAULT_TYPE,
            userId = accountManager.getCurrentUser()?.id.orEmpty(),
            page = 1,
        )
    }

    override fun initObservers() {
        observerRecipeResult()
        observerSaveRecipeResult()
        observerDeleteSavedRecipeResult()
    }

    private fun observerDeleteSavedRecipeResult() {
        recipeViewModel.deleteSavedRecipeResult.observeLiveData(
            viewLifecycleOwner,
            onSuccess = {
                quickMealAdapter.updateSavedRecipe(it.title, false)
            },
            onFailure = {
                showError(getString(R.string.message_failed_delete_recipe))
            },
        )
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun observerSaveRecipeResult() {
        recipeViewModel.saveRecipesResult.observeLiveData(
            lifecycleOwner = viewLifecycleOwner,
            onSuccess = { quickMealAdapter.updateSavedRecipe(it.title, true) },
            onFailure = { showError(getString(R.string.message_failed_save_recipe)) },
        )
    }

    private fun observerRecipeResult() =
        with(binding) {
            recipeViewModel.getRecipesResult.observeLiveData(
                lifecycleOwner = viewLifecycleOwner,
                onLoading = { msvQuickMeals.showLoading() },
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
                },
            )
        }

    override fun initActions() {
        binding.apply {
            imgProfile.onClick {
                ProfileActivity.start(requireContext())
            }

            btnStart.onClick {
                CameraActivity.start(requireContext())
            }
        }
    }

    companion object {
        private const val DEFAULT_TYPE = "sarapan"

        fun newInstance(): HomeFragment = HomeFragment()
    }
}
