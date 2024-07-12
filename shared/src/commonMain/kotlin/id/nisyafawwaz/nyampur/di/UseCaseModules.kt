package id.nisyafawwaz.nyampur.di

import id.nisyafawwaz.nyampur.domain.usecases.RetrieveUserSessionUseCase
import id.nisyafawwaz.nyampur.domain.usecases.SendEmailOtpUseCase
import id.nisyafawwaz.nyampur.domain.usecases.ValidateEmailOtpUseCase
import id.nisyafawwaz.nyampur.ui.AccountManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModules = module {
    singleOf(::SendEmailOtpUseCase)
    singleOf(::ValidateEmailOtpUseCase)
    singleOf(::RetrieveUserSessionUseCase)
    singleOf(::AccountManager)
}