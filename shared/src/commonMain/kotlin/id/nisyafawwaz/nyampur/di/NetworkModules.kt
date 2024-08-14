package id.nisyafawwaz.nyampur.di

import id.nisyafawwaz.nyampur.data.remote.SupabaseConfig
import id.nisyafawwaz.nyampur.httpClient
import org.koin.dsl.module

val networkModule = module {
    single { httpClient }
    single { SupabaseConfig.getInstance() }
}