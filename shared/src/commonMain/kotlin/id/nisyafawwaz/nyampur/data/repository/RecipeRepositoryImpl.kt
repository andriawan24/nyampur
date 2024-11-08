package id.nisyafawwaz.nyampur.data.repository

import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import id.nisyafawwaz.nyampur.data.remote.datasources.api.RemoteDataSource
import id.nisyafawwaz.nyampur.data.remote.datasources.supabase.SupabaseRecipeDataSource
import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.domain.repository.RecipeRepository
import id.nisyafawwaz.nyampur.utils.enums.SortType

class RecipeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val supabaseRecipeDataSource: SupabaseRecipeDataSource
) : RecipeRepository {

    override suspend fun getRecipe(type: String, page: Int): List<RecipeModel> {
        return remoteDataSource.getRecipes(type, page).data.map(RecipeModel::from)
    }

    override suspend fun getSavedRecipes(usersId: String, sortType: SortType): List<RecipeModel> {
        return supabaseRecipeDataSource.getSavedRecipes(usersId, sortType).map(RecipeModel::from)
    }

    override suspend fun saveRecipe(response: RecipeResponse) {
        supabaseRecipeDataSource.saveRecipe(response)
    }

    override suspend fun deleteSavedRecipe(response: RecipeResponse) {
        supabaseRecipeDataSource.deleteSavedRecipe(response)
    }
}