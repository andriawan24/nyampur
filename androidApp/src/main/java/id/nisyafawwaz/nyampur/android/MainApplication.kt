package id.nisyafawwaz.nyampur.android

import android.app.Application
import id.nisyafawwaz.nyampur.android.di.viewModelModules
import id.nisyafawwaz.nyampur.di.appModules
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules + viewModelModules)
        }
    }
}
