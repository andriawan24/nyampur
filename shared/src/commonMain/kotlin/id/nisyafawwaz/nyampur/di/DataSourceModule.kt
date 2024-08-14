package id.nisyafawwaz.nyampur.di

import id.nisyafawwaz.nyampur.data.remote.datasources.RemoteDataSource
import id.nisyafawwaz.nyampur.data.remote.datasources.SupabaseDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataSourceModule = module {
    singleOf(::RemoteDataSource)
    singleOf(::SupabaseDataSource)
}