package id.nisyafawwaz.nyampur.android.ui.camera

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityGalleryBinding
import id.nisyafawwaz.nyampur.android.databinding.LayoutMenuGalleryBinding
import id.nisyafawwaz.nyampur.android.ui.common.ConfirmationBottomSheet
import id.nisyafawwaz.nyampur.android.utils.constants.Extras
import id.nisyafawwaz.nyampur.android.utils.constants.emptyString
import id.nisyafawwaz.nyampur.android.utils.extensions.getPathsFromTakePhotoDir
import id.nisyafawwaz.nyampur.android.utils.extensions.getTakePhotoDirectory
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.extensions.orDefault
import id.nisyafawwaz.nyampur.android.utils.extensions.px
import id.nisyafawwaz.nyampur.android.utils.extensions.setStatusBarInset

class GalleryActivity : BaseActivity<ActivityGalleryBinding>() {
    private val pagerAdapter: GalleryPhotoSliderAdapter by lazy { GalleryPhotoSliderAdapter(this) }
    private val imagePaths: MutableList<String> by lazy { getPathsFromTakePhotoDir().toMutableList() }
    private var imagePathSelected = emptyString()

    override val binding: ActivityGalleryBinding by lazy {
        ActivityGalleryBinding.inflate(layoutInflater)
    }

    private val viewPagerCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.tvPosition.text = getString(R.string.template_current_gallery_position, position + 1, imagePaths.size)
                imagePathSelected = imagePaths.getOrElse(position) { emptyString() }
            }
        }

    override fun initIntent() {
        imagePathSelected = intent.getStringExtra(Extras.EXTRA_IMAGE_PATH).orEmpty()
    }

    override fun initViews() {
        hideSystemUi()
        imagePaths.apply {
            clear()
            addAll(getPathsFromTakePhotoDir())
        }
        binding.viewPagerPhotos.apply {
            adapter = pagerAdapter
            setCurrentItem(imagePaths.indexOf(imagePathSelected).takeIf { index -> index >= 0 }.orDefault(0), true)
            binding.tvPosition.text = getString(R.string.template_current_gallery_position, currentItem + 1, imagePaths.size)
        }
    }

    override fun onResume() {
        super.onResume()

        val updatedImagePaths = getPathsFromTakePhotoDir()
        if (!updatedImagePaths.containsAll(imagePaths)) {
            imagePaths.clear()
            imagePaths.addAll(updatedImagePaths)
            pagerAdapter.notifyItemRangeChanged(0, imagePaths.size)
        }
    }

    private fun hideSystemUi() {
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))
        binding.root.setStatusBarInset()
    }

    override fun initListener() {
        val popupWindow = setupPopupWindow()
        binding.apply {
            btnBack.onClick {
                finish()
            }

            btnMenu.onClick {
                popupWindow?.showAsDropDown(btnMenu, (-70).px, 12.px)
            }

            viewPagerPhotos.registerOnPageChangeCallback(viewPagerCallback)

            supportFragmentManager.setFragmentResultListener(
                ConfirmationBottomSheet.KEY_CONFIRMATION_REQUEST,
                this@GalleryActivity,
            ) { _, bundle ->
                if (bundle.getBoolean(ConfirmationBottomSheet.KEY_CONFIRMATION_RETURN, false)) {
                    // Delete file
                    val imageFile = getTakePhotoDirectory().listFiles { pathname -> pathname.path == imagePathSelected }?.firstOrNull()
                    val isDeleted = imageFile?.delete()

                    if (isDeleted == true) {
                        val idx = imagePaths.indexOf(imagePathSelected)
                        imagePaths.remove(imagePathSelected)
                        pagerAdapter.notifyItemRemoved(idx)

                        // Check if all images are deleted
                        if (imagePaths.isNotEmpty()) {
                            binding.apply {
                                tvPosition.text = getString(R.string.template_current_gallery_position, 1, imagePaths.size)
                            }
                        } else {
                            finish()
                        }
                    } else {
                        // TODO: Handle failed to delete file
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewPagerPhotos.unregisterOnPageChangeCallback(viewPagerCallback)
    }

    private fun setupPopupWindow(): PopupWindow? {
        val popupInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as? LayoutInflater
        if (popupInflater != null) {
            val popupBind = LayoutMenuGalleryBinding.inflate(popupInflater)

            val popupWindow =
                PopupWindow(popupBind.root, 116.px.toInt(), RelativeLayout.LayoutParams.WRAP_CONTENT, true).apply {
                    contentView.onClick {
                        dismiss()
                    }
                }

            popupBind.btnDelete.onClick {
                popupWindow.dismiss()
                ConfirmationBottomSheet.newInstance(
                    title = getString(R.string.title_delete_this_photo),
                    message = getString(R.string.message_delete_this_photo),
                    buttonPositiveTitle = getString(R.string.action_delete),
                    buttonNegativeTitle = getString(R.string.action_cancel),
                ).show(supportFragmentManager, ConfirmationBottomSheet::class.simpleName)
            }

            return popupWindow
        }

        return null
    }

    inner class GalleryPhotoSliderAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
        override fun createFragment(position: Int): Fragment {
            return GalleryItemFragment.newInstance(imagePaths[position])
        }

        override fun getItemCount(): Int = imagePaths.size
    }

    companion object {
        fun start(
            context: Context,
            imageSelectedPath: String,
        ) {
            val intent =
                Intent(context, GalleryActivity::class.java).apply {
                    putExtra(Extras.EXTRA_IMAGE_PATH, imageSelectedPath)
                }
            context.startActivity(intent)
        }
    }
}
