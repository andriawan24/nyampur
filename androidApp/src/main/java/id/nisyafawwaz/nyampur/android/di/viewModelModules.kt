package id.nisyafawwaz.nyampur.android.di

import id.nisyafawwaz.nyampur.ui.AuthVM
import id.nisyafawwaz.nyampur.ui.RecipeVM
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModules = module {
    viewModelOf(::AuthVM)
    viewModelOf(::RecipeVM)
}