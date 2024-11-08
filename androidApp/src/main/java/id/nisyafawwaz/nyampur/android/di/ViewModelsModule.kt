package id.nisyafawwaz.nyampur.android.di

import id.nisyafawwaz.nyampur.ui.AuthenticationViewModel
import id.nisyafawwaz.nyampur.ui.RecipeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModules =
    module {
        viewModelOf(::AuthenticationViewModel)
        viewModelOf(::RecipeViewModel)
    }
