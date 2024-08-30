package id.nisyafawwaz.nyampur.android.ui.main.saved

import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import id.nisyafawwaz.nyampur.android.adapters.SavedRecipeAdapter
import id.nisyafawwaz.nyampur.android.base.BaseFragment
import id.nisyafawwaz.nyampur.android.databinding.FragmentSavedBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.observeLiveData
import id.nisyafawwaz.nyampur.android.utils.extensions.showDefault
import id.nisyafawwaz.nyampur.android.utils.extensions.showEmpty
import id.nisyafawwaz.nyampur.android.utils.extensions.showError
import id.nisyafawwaz.nyampur.android.utils.extensions.showLoading
import id.nisyafawwaz.nyampur.ui.AccountManager
import id.nisyafawwaz.nyampur.ui.RecipeVM
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedFragment : BaseFragment<FragmentSavedBinding>() {

    private val recipeVM: RecipeVM by viewModel()
    private val accountManager: AccountManager by inject()
    private val savedRecipeAdapter = SavedRecipeAdapter()

    override val binding: FragmentSavedBinding by lazy {
        FragmentSavedBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        initAdapter()
        initSortDropdown()
    }

    private fun initSortDropdown() = Unit

    private fun initAdapter() {
        binding.rvSaved.apply {
            adapter = savedRecipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun initProcess() {
        recipeVM.getSavedRecipes(accountManager.getCurrentUser()?.id.orEmpty())
    }

    override fun initActions() = Unit

    override fun initObservers() {
        observeSavedRecipesResult()
    }

    private fun observeSavedRecipesResult() {
        recipeVM.getSavedRecipesResult.observeLiveData(
            this,
            onEmpty = {
                binding.msvSavedRecipes.showEmpty()
            },
            onLoading = {
                binding.msvSavedRecipes.showLoading()
            },
            onSuccess = {
                binding.msvSavedRecipes.showDefault()
                savedRecipeAdapter.addAll(it)
            },
            onFailure = {
                binding.msvSavedRecipes.showError()
            }
        )
    }

    companion object {
        fun newInstance(): SavedFragment = SavedFragment()
    }
}