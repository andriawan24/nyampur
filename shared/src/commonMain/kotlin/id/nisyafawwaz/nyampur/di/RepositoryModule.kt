package id.nisyafawwaz.nyampur.di

import id.nisyafawwaz.nyampur.data.repository.AuthRepositoryImpl
import id.nisyafawwaz.nyampur.domain.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
}