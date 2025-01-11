package id.nisyafawwaz.nyampur.android.ui.camera

import android.content.Context
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.GridLayoutManager
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.adapters.PhotosItemAdapter
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityImagesResultBinding
import id.nisyafawwaz.nyampur.android.ui.common.ConfirmationBottomSheet
import id.nisyafawwaz.nyampur.android.ui.ingredients.IngredientListActivity
import id.nisyafawwaz.nyampur.android.utils.extensions.getPathsFromTakePhotoDir
import id.nisyafawwaz.nyampur.android.utils.extensions.getTakePhotoDirectory
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.extensions.px
import id.nisyafawwaz.nyampur.android.utils.list.GridItemDecoration

class ImagesResultActivity : BaseActivity<ActivityImagesResultBinding>() {
    private val imagePaths = mutableListOf<String>()
    private val photosAdapter: PhotosItemAdapter by lazy {
        PhotosItemAdapter(
            onPhotoItemClicked = { imagePath ->
                GalleryActivity.start(this@ImagesResultActivity, imagePath)
            },
            onAddPhotoClicked = {
                startActivity(CameraActivity.createIntent(this))
            },
        )
    }

    override val binding: ActivityImagesResultBinding by lazy {
        ActivityImagesResultBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        initConfirmationListener()
        initRecycler()
    }

    private fun initConfirmationListener() {
        supportFragmentManager.setFragmentResultListener(ConfirmationBottomSheet.KEY_CONFIRMATION_REQUEST, this) { _, bundle ->
            if (bundle.getBoolean(ConfirmationBottomSheet.KEY_CONFIRMATION_RETURN, false)) {
                getTakePhotoDirectory().deleteRecursively()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val updatedImagePaths = getPathsFromTakePhotoDir()
        if (imagePaths != updatedImagePaths) {
            imagePaths.clear()
            imagePaths.addAll(updatedImagePaths)
            photosAdapter.addAll(imagePaths)
        }
    }

    private fun initRecycler() {
        binding.rvPhotos.apply {
            layoutManager = GridLayoutManager(this@ImagesResultActivity, 3)
            addItemDecoration(GridItemDecoration(gridCount = 3, verticalMargin = 20.px))
            adapter = photosAdapter
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressedDispatcher.onBackPressed()
            }

            btnContinue.onClick {
                IngredientListActivity.start(this@ImagesResultActivity)
            }

            onBackPressedDispatcher.addCallback(
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        ConfirmationBottomSheet.newInstance(
                            title = getString(R.string.title_back_to_home),
                            message = getString(R.string.message_deleted_progress),
                            buttonPositiveTitle = getString(R.string.action_yes),
                            buttonNegativeTitle = getString(R.string.action_cancel),
                        ).show(supportFragmentManager, ConfirmationBottomSheet::class.simpleName)
                    }
                },
            )
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ImagesResultActivity::class.java))
        }
    }
}
