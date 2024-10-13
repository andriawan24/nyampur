package id.nisyafawwaz.nyampur.domain.usecases.recipes

import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.repository.RecipeRepository
import id.nisyafawwaz.nyampur.utils.enums.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSavedRecipeUseCase(private val recipeRepository: RecipeRepository) {

    fun execute(userId: String, sortType: SortType): Flow<ResultState<List<RecipeModel>>> = flow {
        emit(ResultState.Loading)
        try {
            val response = recipeRepository.getSavedRecipes(userId, sortType)
            if (response.isNotEmpty()) {
                emit(ResultState.Success(response))
            } else {
                emit(ResultState.Empty)
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }
}