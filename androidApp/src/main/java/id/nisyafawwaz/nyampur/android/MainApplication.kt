package id.nisyafawwaz.nyampur.android

import android.app.Application
import android.util.Log
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraXConfig
import id.nisyafawwaz.nyampur.android.di.viewModelModules
import id.nisyafawwaz.nyampur.di.appModules
import org.koin.core.context.startKoin

class MainApplication : Application(), CameraXConfig.Provider {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules + viewModelModules)
        }
    }

    override fun getCameraXConfig(): CameraXConfig {
        return CameraXConfig.Builder.fromConfig(Camera2Config.defaultConfig())
            .setMinimumLoggingLevel(Log.ERROR)
            .setAvailableCamerasLimiter(CameraSelector.DEFAULT_BACK_CAMERA)
            .build()
    }
}
