package id.nisyafawwaz.nyampur.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.databinding.ItemQuickMealsBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.domain.models.RecipeModel

class QuickMealAdapter(
    private val onFavoriteClicked: (data: RecipeModel) -> Unit
) : RecyclerView.Adapter<QuickMealAdapter.QuickMealViewHolder>() {

    private var recipeDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    inner class QuickMealViewHolder(
        private val binding: ItemQuickMealsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeModel) {
            with(binding) {
                tvFoodName.text = recipe.title
                tvCookTime.text = recipe.cookTime.toString()
                tvLevel.text = recipe.level

                Glide.with(root.context)
                    .load(recipe.imageUrl)
                    .placeholder(R.drawable.img_food_placeholder)
                    .error(R.drawable.img_food_placeholder)
                    .into(ivFood)

                btnFavorite.onClick {
                    onFavoriteClicked.invoke(recipe)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickMealViewHolder {
        return QuickMealViewHolder(
            ItemQuickMealsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = recipeDiffer.currentList.size

    override fun onBindViewHolder(holder: QuickMealViewHolder, position: Int) {
        holder.bind(recipeDiffer.currentList[position])
    }

    fun addAll(newData: List<RecipeModel>) {
        recipeDiffer.submitList(newData)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecipeModel>() {
            override fun areItemsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}