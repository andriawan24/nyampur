package id.nisyafawwaz.nyampur.di

import id.nisyafawwaz.nyampur.domain.usecases.auth.RetrieveUserSessionUseCase
import id.nisyafawwaz.nyampur.domain.usecases.auth.SendEmailOtpUseCase
import id.nisyafawwaz.nyampur.domain.usecases.auth.ValidateEmailOtpUseCase
import id.nisyafawwaz.nyampur.domain.usecases.recipes.GetRecipesUseCase
import id.nisyafawwaz.nyampur.ui.AccountManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModules = module {
    singleOf(::SendEmailOtpUseCase)
    singleOf(::ValidateEmailOtpUseCase)
    singleOf(::RetrieveUserSessionUseCase)
    singleOf(::AccountManager)

    singleOf(::GetRecipesUseCase)
}