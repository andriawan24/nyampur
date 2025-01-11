package id.nisyafawwaz.nyampur.android.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.nisyafawwaz.nyampur.android.databinding.ItemIngredientBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.domain.models.IngredientModel

class IngredientAdapter(private val onEditClicked: (IngredientModel) -> Unit) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    private var ingredientsDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    fun addAll(newData: List<IngredientModel>) {
        ingredientsDiffer.submitList(newData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    inner class ViewHolder(
        private val binding: ItemIngredientBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: IngredientModel) {
            with(binding) {
                tvName.text = ingredient.name
                ivRecipe.setImageURI(Uri.parse(ingredient.imagePath))
                btnEdit.onClick {
                    onEditClicked.invoke(ingredient)
                }
            }
        }
    }

    override fun getItemCount(): Int = ingredientsDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(ingredientsDiffer.currentList[position])
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<IngredientModel>() {
                override fun areItemsTheSame(
                    oldItem: IngredientModel,
                    newItem: IngredientModel,
                ): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(
                    oldItem: IngredientModel,
                    newItem: IngredientModel,
                ): Boolean {
                    return oldItem.name == newItem.name
                }
            }
    }
}
