package id.nisyafawwaz.nyampur.android.ui.camera

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import id.nisyafawwaz.nyampur.android.adapters.PhotosItemAdapter
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityImagesResultBinding
import id.nisyafawwaz.nyampur.android.utils.constants.Extras
import id.nisyafawwaz.nyampur.android.utils.constants.emptyString
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.extensions.px
import id.nisyafawwaz.nyampur.android.utils.list.GridItemDecoration

class ImagesResultActivity : BaseActivity<ActivityImagesResultBinding>() {
    private val addImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imagePath = result.data?.getStringExtra(CameraActivity.KEY_IMAGE_PATH).orEmpty()
                if (imagePath.isNotBlank()) {
                    handleImageAdded(imagePath)
                }
            }
        }

    private val imagePathList = mutableListOf<String>()
    private var imagePath = emptyString()
    private val photosAdapter: PhotosItemAdapter by lazy {
        PhotosItemAdapter(
            onPhotoItemClicked = { imagePath ->
            },
            onAddPhotoClicked = {
                addImageLauncher.launch(CameraActivity.createIntent(this))
            },
        )
    }

    override val binding: ActivityImagesResultBinding by lazy {
        ActivityImagesResultBinding.inflate(layoutInflater)
    }

    override fun initIntent() {
        imagePath = intent.getStringExtra(Extras.EXTRA_IMAGE_PATH).orEmpty()
        if (imagePath.isBlank()) {
            Toast.makeText(this, "Couldn't find the image path", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun initViews() {
        imagePathList.add(imagePath)
        initRecycler()
    }

    private fun handleImageAdded(path: String) {
        imagePathList.add(path)
        photosAdapter.initData(imagePathList)
    }

    private fun initRecycler() {
        binding.rvPhotos.apply {
            layoutManager = GridLayoutManager(this@ImagesResultActivity, 3)
            addItemDecoration(GridItemDecoration(gridCount = 3, verticalMargin = 20.px))
            adapter = photosAdapter
            photosAdapter.initData(imagePathList)
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                finish()
            }

            btnContinue.onClick {
            }
        }
    }

    companion object {
        fun start(
            context: Context,
            imagePath: String,
        ) {
            val intent =
                Intent(context, ImagesResultActivity::class.java).apply {
                    putExtra(Extras.EXTRA_IMAGE_PATH, imagePath)
                }

            context.startActivity(intent)
        }
    }
}
