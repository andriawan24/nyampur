package id.nisyafawwaz.nyampur.domain.repository

import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import id.nisyafawwaz.nyampur.domain.models.RecipeModel

interface RecipeRepository {
    suspend fun getRecipe(type: String, page: Int): List<RecipeModel>
    suspend fun getSavedRecipes(usersId: String): List<RecipeModel>
    suspend fun saveRecipe(response: RecipeResponse): RecipeModel
    suspend fun deleteSavedRecipe(response: RecipeResponse): RecipeModel
}