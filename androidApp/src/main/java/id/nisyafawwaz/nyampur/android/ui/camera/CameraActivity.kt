package id.nisyafawwaz.nyampur.android.ui.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.common.util.concurrent.ListenableFuture
import id.nisyafawwaz.nyampur.android.R
import id.nisyafawwaz.nyampur.android.base.BaseActivity
import id.nisyafawwaz.nyampur.android.databinding.ActivityCameraBinding
import id.nisyafawwaz.nyampur.android.utils.constants.Extras
import id.nisyafawwaz.nyampur.android.utils.extensions.getTakePhotoDirectory
import id.nisyafawwaz.nyampur.android.utils.extensions.onClick
import id.nisyafawwaz.nyampur.android.utils.logs.debug
import kotlinx.coroutines.Runnable
import java.io.File
import java.util.concurrent.Executors

class CameraActivity : BaseActivity<ActivityCameraBinding>() {
    private var isReturnResult = false
    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startCamera()
            } else {
                Toast.makeText(this, getString(R.string.message_camera_permission_required), Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    private val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> by lazy {
        ProcessCameraProvider.getInstance(this)
    }

    private val imageCapture: ImageCapture by lazy {
        ImageCapture.Builder()
            .setTargetRotation(binding.root.display.rotation)
            .build()
    }

    private val cameraExecutor = Executors.newSingleThreadExecutor()

    override val binding: ActivityCameraBinding by lazy {
        ActivityCameraBinding.inflate(layoutInflater)
    }

    override fun initIntent() {
        isReturnResult = intent.getBooleanExtra(Extras.EXTRA_RETURN_RESULT, false)
    }

    override fun initViews() {
        hideSystemUi()
        startCamera()
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun startCamera() {
        if (checkCameraPermission()) {
            cameraProviderFuture.addListener(
                Runnable {
                    val provider = cameraProviderFuture.get()
                    bindPreview(provider)
                },
                ContextCompat.getMainExecutor(this),
            )
        } else {
            requestCameraPermission()
        }
    }

    private fun bindPreview(provider: ProcessCameraProvider) {
        val preview = Preview.Builder().build()
        val cameraSelector =
            CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

        preview.surfaceProvider = binding.previewCamera.surfaceProvider
        provider.bindToLifecycle(this, cameraSelector, imageCapture, preview)
    }

    private fun requestCameraPermission() {
        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                finish()
            }

            btnShutter.onClick {
                takePhoto()
            }
        }
    }

    private fun takePhoto() {
        val outputFile = File(getTakePhotoDirectory(), "${System.currentTimeMillis()}.jpg")
        val outputOption = ImageCapture.OutputFileOptions.Builder(outputFile).build()

        imageCapture.takePicture(
            outputOption,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    debug { "imageCaptureException: ${exception.message}" }
                    Toast.makeText(this@CameraActivity, "There is an error while taking photo", Toast.LENGTH_SHORT).show()
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    outputFileResults.savedUri?.let { uri ->
                        if (isReturnResult) {
                            val intent =
                                Intent().apply {
                                    putExtra(KEY_IMAGE_PATH, uri.toString())
                                }
                            setResult(RESULT_OK, intent)
                            finish()
                        } else {
                            ImagesResultActivity.start(this@CameraActivity, uri.toString())
                            finish()
                        }
                    }
                }
            },
        )
    }

    companion object {
        const val KEY_IMAGE_PATH = "KEY_IMAGE_PATH"

        fun start(context: Context) {
            val intent =
                Intent(context, CameraActivity::class.java).apply {
                    putExtra(Extras.EXTRA_RETURN_RESULT, false)
                }
            context.startActivity(intent)
        }

        fun createIntent(context: Context): Intent {
            return Intent(context, CameraActivity::class.java).apply {
                putExtra(Extras.EXTRA_RETURN_RESULT, true)
            }
        }
    }
}
