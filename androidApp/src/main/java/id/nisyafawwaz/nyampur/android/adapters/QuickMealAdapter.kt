package id.nisyafawwaz.nyampur.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.databinding.ItemQuickMealsBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import java.util.Locale

class QuickMealAdapter(
    private val onFavoriteClicked: (data: RecipeModel) -> Unit,
) : RecyclerView.Adapter<QuickMealAdapter.QuickMealViewHolder>() {
    private var recipeDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    inner class QuickMealViewHolder(
        private val binding: ItemQuickMealsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeModel) {
            with(binding) {
                tvFoodName.text = recipe.title
                tvCookTime.text = String.format(Locale.getDefault(), "%d", recipe.cookTime)
                tvLevel.text = "Hello World"

                Glide.with(root.context)
                    .load(recipe.imageUrl)
                    .placeholder(R.drawable.img_food_placeholder)
                    .error(R.drawable.img_food_placeholder)
                    .into(ivFood)

                btnFavorite.apply {
                    icon = ContextCompat.getDrawable(
                        root.context,
                        if (recipe.isSaved) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline,
                    )
                    onClick {
                        onFavoriteClicked.invoke(recipe)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): QuickMealViewHolder {
        return QuickMealViewHolder(
            ItemQuickMealsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int = recipeDiffer.currentList.size

    override fun onBindViewHolder(
        holder: QuickMealViewHolder,
        position: Int,
    ) {
        holder.bind(recipeDiffer.currentList[position])
    }

    fun updateSavedRecipe(
        name: String,
        isSaved: Boolean,
    ) {
        val recipeIndex = recipeDiffer.currentList.indexOfFirst { it.title == name }
        if (recipeIndex != -1) {
            recipeDiffer.currentList[recipeIndex].isSaved = isSaved
            notifyItemChanged(recipeIndex)
        }
    }

    fun addAll(newData: List<RecipeModel>) {
        recipeDiffer.submitList(newData)
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<RecipeModel>() {
                override fun areItemsTheSame(
                    oldItem: RecipeModel,
                    newItem: RecipeModel,
                ): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(
                    oldItem: RecipeModel,
                    newItem: RecipeModel,
                ): Boolean {
                    return oldItem.title == newItem.title
                }
            }
    }
}
