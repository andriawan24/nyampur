package id.nisyafawwaz.nyampur.domain.usecases.recipes

import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveRecipeUseCase(private val recipeRepository: RecipeRepository) {

    fun execute(response: RecipeResponse): Flow<ResultState<RecipeModel>> = flow {
        emit(ResultState.Loading)
        try {
            recipeRepository.saveRecipe(response)
            emit(ResultState.Success(RecipeModel.from(response)))
        } catch (e: Exception) {
            print(e.message)
            emit(ResultState.Error(e))
        }
    }
}