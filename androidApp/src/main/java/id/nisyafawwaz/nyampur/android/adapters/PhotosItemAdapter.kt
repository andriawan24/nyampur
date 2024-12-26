package id.nisyafawwaz.nyampur.android.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.nisyafawwaz.nyampur.android.databinding.ItemAddPhotoBinding
import id.nisyafawwaz.nyampur.android.databinding.ItemPhotoBinding
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick

class PhotosItemAdapter(
    private val onPhotoItemClicked: (String) -> Unit,
    private val onAddPhotoClicked: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val photosDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    fun initData(imagePaths: List<String>) {
        photosDiffer.submitList(imagePaths)
    }

    inner class PhotosItemViewHolder(
        private val binding: ItemPhotoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePath: String) {
            binding.apply {
                imgFood.setImageURI(Uri.parse(imagePath))
                imgFood.onClick {
                    onPhotoItemClicked.invoke(imagePath)
                }
            }
        }
    }

    inner class AddPhotosItemViewHolder(private val binding: ItemAddPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.btnAdd.onClick {
                onAddPhotoClicked.invoke()
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is PhotosItemViewHolder -> holder.bind(photosDiffer.currentList[position])
            is AddPhotosItemViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM_PHOTO) {
            PhotosItemViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            AddPhotosItemViewHolder(ItemAddPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < photosDiffer.currentList.size) VIEW_TYPE_ITEM_PHOTO else VIEW_TYPE_ADD_PHOTO
    }

    override fun getItemCount(): Int = photosDiffer.currentList.size + 1

    companion object {
        const val VIEW_TYPE_ITEM_PHOTO = 1
        const val VIEW_TYPE_ADD_PHOTO = 2

        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(
                    oldItem: String,
                    newItem: String,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: String,
                    newItem: String,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
