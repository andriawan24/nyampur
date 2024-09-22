package id.nisyafawwaz.nyampur.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.usecases.recipes.DeleteSavedRecipeUseCase
import id.nisyafawwaz.nyampur.domain.usecases.recipes.GetRecipesUseCase
import id.nisyafawwaz.nyampur.domain.usecases.recipes.GetSavedRecipeUseCase
import id.nisyafawwaz.nyampur.domain.usecases.recipes.SaveRecipeUseCase
import id.nisyafawwaz.nyampur.utils.enums.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private typealias GetRecipesResult = ResultState<List<RecipeModel>>

class RecipeVM : ViewModel(), KoinComponent {

    private val getRecipesUseCase: GetRecipesUseCase by inject()
    private val saveRecipeUseCase: SaveRecipeUseCase by inject()
    private val getSavedRecipeUseCase: GetSavedRecipeUseCase by inject()
    private val deleteSavedRecipeUseCase: DeleteSavedRecipeUseCase by inject()

    private val _getRecipesResult = MutableStateFlow<GetRecipesResult>(ResultState.Idle)
    val getRecipesResult = _getRecipesResult.asStateFlow()

    private val _saveRecipesResult = MutableStateFlow<ResultState<RecipeModel>>(ResultState.Idle)
    val saveRecipesResult = _saveRecipesResult.asStateFlow()

    private val _deleteSavedRecipeResult = MutableStateFlow<ResultState<RecipeModel>>(ResultState.Idle)
    val deleteSavedRecipeResult = _deleteSavedRecipeResult.asStateFlow()

    private val _getSavedRecipesResult = MutableStateFlow<ResultState<List<RecipeModel>>>(ResultState.Idle)
    val getSavedRecipesResult = _getSavedRecipesResult.asStateFlow()

    fun getRecipes(type: String, userId: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getRecipesUseCase.execute(type, userId, page).collectLatest {
                _getRecipesResult.emit(it)
            }
        }
    }

    fun getSavedRecipes(userId: String, sortType: SortType = SortType.RECENTLY) {
        viewModelScope.launch(Dispatchers.IO) {
            getSavedRecipeUseCase.execute(userId, sortType).collectLatest {
                _getSavedRecipesResult.emit(it)
            }
        }
    }

    fun saveRecipe(response: RecipeResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            saveRecipeUseCase.execute(response).collectLatest {
                _saveRecipesResult.emit(it)
            }
        }
    }

    fun deleteSavedRecipe(response: RecipeResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteSavedRecipeUseCase.execute(response).collectLatest {
                _deleteSavedRecipeResult.emit(it)
            }
        }
    }
}