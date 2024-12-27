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
import id.nisyafawwaz.nyampur.android.utils.extensions.getTakePhotoDirectory
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.extensions.px
import id.nisyafawwaz.nyampur.android.utils.list.GridItemDecoration

class ImagesResultActivity : BaseActivity<ActivityImagesResultBinding>() {
    private val imagePathList = mutableListOf<String>()
    private val photosAdapter: PhotosItemAdapter by lazy {
        PhotosItemAdapter(
            onPhotoItemClicked = { imagePath ->
                GalleryActivity.start(this@ImagesResultActivity)
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
        initRecycler()
    }

    override fun onResume() {
        super.onResume()

        val imagePaths = getTakePhotoDirectory().listFiles().orEmpty().map { file -> file.path }
        imagePathList.clear()
        imagePathList.addAll(imagePaths)
        photosAdapter.addAll(imagePathList)
    }

    private fun initRecycler() {
        binding.rvPhotos.apply {
            layoutManager = GridLayoutManager(this@ImagesResultActivity, 3)
            addItemDecoration(GridItemDecoration(gridCount = 3, verticalMargin = 20.px))
            adapter = photosAdapter
        }
    }

    override fun initListener() {
        supportFragmentManager.setFragmentResultListener(ConfirmationBottomSheet.KEY_CONFIRMATION_REQUEST, this) { _, bundle ->
            if (bundle.getBoolean(ConfirmationBottomSheet.KEY_CONFIRMATION_RETURN, false)) {
                getTakePhotoDirectory().deleteRecursively()
                finish()
            }
        }

        binding.apply {
            btnBack.onClick {
                onBackPressedDispatcher.onBackPressed()
            }

            btnContinue.onClick {
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
