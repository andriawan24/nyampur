package id.nisyafawwaz.nyampur.domain.usecases.recipes

import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipesUseCase(private val recipeRepository: RecipeRepository) {

    fun execute(type: String, page: Int): Flow<ResultState<List<RecipeModel>>> = flow {
        emit(ResultState.Loading)
        try {
            val user = recipeRepository.getRecipe(type, page)
            emit(ResultState.Success(user))
        } catch (e: Exception) {
            print(e.message)
            emit(ResultState.Error(e))
        }
    }
}