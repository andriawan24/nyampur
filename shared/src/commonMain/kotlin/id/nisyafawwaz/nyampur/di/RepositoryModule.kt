package id.nisyafawwaz.nyampur.di

import id.nisyafawwaz.nyampur.data.repository.AuthRepositoryImpl
import id.nisyafawwaz.nyampur.data.repository.RecipeRepositoryImpl
import id.nisyafawwaz.nyampur.domain.repository.AuthRepository
import id.nisyafawwaz.nyampur.domain.repository.RecipeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<RecipeRepository> { RecipeRepositoryImpl(get(), get()) }
}