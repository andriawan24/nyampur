package id.nisyafawwaz.nyampur.domain.usecases.recipes

import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipesUseCase(private val recipeRepository: RecipeRepository) {

    fun execute(type: String, userId: String, page: Int): Flow<ResultState<List<RecipeModel>>> = flow {
        emit(ResultState.Loading)
        try {
            val recipes = recipeRepository.getRecipe(type, page)

            if (userId.isNotBlank()) {
                val savedRecipes = recipeRepository.getSavedRecipes(userId)
                recipes.forEach { recipe ->
                    if (savedRecipes.any { it.title.contains(recipe.title, true) }) {
                        recipe.isSaved = true
                    }
                }
            }

            if (recipes.isEmpty()) {
                emit(ResultState.Empty)
            } else {
                emit(ResultState.Success(recipes))
            }
        } catch (e: Exception) {
            print(e.message)
            emit(ResultState.Error(e))
        }
    }
}