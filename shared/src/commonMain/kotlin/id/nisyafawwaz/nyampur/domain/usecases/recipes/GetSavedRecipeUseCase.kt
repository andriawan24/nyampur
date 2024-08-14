package id.nisyafawwaz.nyampur.domain.usecases.recipes

import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSavedRecipeUseCase(private val recipeRepository: RecipeRepository) {

    fun execute(userId: String): Flow<ResultState<List<RecipeModel>>> = flow {
        emit(ResultState.Loading)
        try {
            val response = recipeRepository.getSavedRecipes(userId)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            print(e.message)
            emit(ResultState.Error(e))
        }
    }
}