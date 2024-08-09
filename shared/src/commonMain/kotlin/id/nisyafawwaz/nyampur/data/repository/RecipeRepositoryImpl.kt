package id.nisyafawwaz.nyampur.data.repository

import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import id.nisyafawwaz.nyampur.data.remote.RemoteDataSource
import id.nisyafawwaz.nyampur.data.remote.SupabaseDataSource
import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val supabaseDataSource: SupabaseDataSource
) : RecipeRepository {

    override suspend fun getRecipe(type: String, page: Int): List<RecipeModel> {
        return remoteDataSource.getRecipes(type, page).data.map(RecipeModel::from)
    }

    override suspend fun saveRecipe(response: RecipeResponse) {
        supabaseDataSource.saveRecipe(response)
    }
}