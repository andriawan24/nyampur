package id.nisyafawwaz.nyampur.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.usecases.recipes.GetRecipesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private typealias GetRecipesResult = ResultState<List<RecipeModel>>

class RecipeViewModel : ViewModel(), KoinComponent {

    private val getRecipesUseCase: GetRecipesUseCase by inject()

    private val _getRecipesResult = MutableStateFlow<GetRecipesResult>(ResultState.Idle)
    val getRecipesResult = _getRecipesResult.asStateFlow()

    fun getRecipes(type: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getRecipesUseCase.execute(type, page).collectLatest {
                _getRecipesResult.emit(it)
            }
        }
    }
}