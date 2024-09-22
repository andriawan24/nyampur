package id.nisyafawwaz.nyampur.domain.repository

import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.utils.enums.SortType

interface RecipeRepository {
    suspend fun getRecipe(type: String, page: Int): List<RecipeModel>
    suspend fun getSavedRecipes(usersId: String, sortType: SortType): List<RecipeModel>
    suspend fun saveRecipe(response: RecipeResponse): RecipeModel
    suspend fun deleteSavedRecipe(response: RecipeResponse): RecipeModel
}