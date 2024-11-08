package id.nisyafawwaz.nyampur.android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.databinding.ItemSavedRecipeBinding
import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import java.util.Locale

class SavedRecipeAdapter : RecyclerView.Adapter<SavedRecipeAdapter.ViewHolder>() {
    private var recipeDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    fun addAll(newData: List<RecipeModel>) {
        recipeDiffer.submitList(newData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            ItemSavedRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    inner class ViewHolder(
        private val binding: ItemSavedRecipeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeModel) {
            with(binding) {
                tvFoodName.text = recipe.title
                tvCookTime.text = String.format(Locale.getDefault(), "%d", recipe.cookTime)
                tvLevel.text = recipe.level

                Glide.with(root.context)
                    .load(recipe.imageUrl)
                    .placeholder(R.drawable.img_food_placeholder)
                    .error(R.drawable.img_food_placeholder)
                    .into(imgFood)
            }
        }
    }

    override fun getItemCount(): Int = recipeDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(recipeDiffer.currentList[position])
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
