package id.nisyafawwaz.nyampur.android.di

import id.nisyafawwaz.nyampur.ui.AuthenticationViewModel
import id.nisyafawwaz.nyampur.ui.RecipeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModules = module {
    viewModelOf(::AuthenticationViewModel)
    viewModelOf(::RecipeViewModel)
}