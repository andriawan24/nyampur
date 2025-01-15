package id.nisyafawwaz.nyampur.android.ui.ingredients

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import id.nisyafawwaz.nyampur.android.adapters.IngredientAdapter
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityIngredientListBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.disable
import id.nisyafawwaz.nyampur.android.utils.extensions.enable
import id.nisyafawwaz.nyampur.android.utils.extensions.getPathsFromTakePhotoDir
import id.nisyafawwaz.nyampur.android.utils.extensions.observeLiveData
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.list.SpacerItemDecoration
import id.nisyafawwaz.nyampur.domain.models.IngredientModel
import id.nisyafawwaz.nyampur.ui.GeminiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class IngredientListActivity : BaseActivity<ActivityIngredientListBinding>() {
    private val geminiViewModel: GeminiViewModel by viewModel()

    private val imagePaths = mutableListOf<String>()
    private val ingredients = mutableListOf<IngredientModel>()
    private val ingredientAdapter: IngredientAdapter by lazy {
        IngredientAdapter(
            onEditClicked = {
            },
        )
    }

    override val binding: ActivityIngredientListBinding by lazy {
        ActivityIngredientListBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        initRecycler()
    }

    private fun initRecycler() {
        binding.apply {
            rvIngredient.adapter = ingredientAdapter
            rvIngredient.layoutManager = LinearLayoutManager(this@IngredientListActivity)
            rvIngredient.addItemDecoration(SpacerItemDecoration())
        }
    }

    override fun onResume() {
        super.onResume()
        val updatedImagePaths = getPathsFromTakePhotoDir()
        if (imagePaths != updatedImagePaths) {
            imagePaths.clear()
            imagePaths.addAll(updatedImagePaths)
            fetchIngredients()
        }
    }

    private fun fetchIngredients() {
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                binding.apply {
                    btnBack.disable()
                    btnContinue.disable()
                }

                Toast.makeText(
                    this@IngredientListActivity,
                    "Extracting ingredients, please wait...",
                    Toast.LENGTH_SHORT,
                ).show()
            }

            val images =
                imagePaths.map { path ->
                    val bitmap = BitmapFactory.decodeFile(path)
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    return@map stream.toByteArray()
                }

            geminiViewModel.extractIngredients(images)
        }
    }

    override fun initListener() {
        binding.btnBack.onClick {
            finish()
        }
    }

    override fun initObserver() {
        geminiViewModel.ingredientsResult.observeLiveData(
            this,
            onLoading = {
                binding.apply {
                    btnBack.disable()
                    btnContinue.disable()
                }
            },
            onSuccess = {
                binding.apply {
                    btnBack.enable()
                    btnContinue.enable()
                }
                ingredients.clear()
                ingredients.addAll(
                    it.mapIndexed { index, ingredient ->
                        ingredient.imagePath = imagePaths.getOrNull(index).orEmpty()
                        ingredient
                    },
                )
                ingredientAdapter.addAll(ingredients)
            },
            onFailure = {
                binding.apply {
                    btnBack.enable()
                    btnContinue.enable()
                }
                Toast.makeText(this, it.message.orEmpty(), Toast.LENGTH_SHORT).show()
            },
        )
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, IngredientListActivity::class.java))
        }
    }
}
