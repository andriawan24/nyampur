package id.nisyafawwaz.nyampur.domain.repository

import id.nisyafawwaz.nyampur.domain.models.RecipeModel

interface RecipeRepository {
    suspend fun getRecipe(type: String, page: Int): List<RecipeModel>
}