package id.nisyafawwaz.nyampur.data.repository

import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import id.nisyafawwaz.nyampur.data.remote.datasources.RemoteDataSource
import id.nisyafawwaz.nyampur.data.remote.datasources.SupabaseDataSource
import id.nisyafawwaz.nyampur.domain.models.RecipeModel
import id.nisyafawwaz.nyampur.domain.repository.RecipeRepository
import id.nisyafawwaz.nyampur.utils.enums.SortType

class RecipeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val supabaseDataSource: SupabaseDataSource
) : RecipeRepository {

    override suspend fun getRecipe(type: String, page: Int): List<RecipeModel> {
        return remoteDataSource.getRecipes(type, page).data.map(RecipeModel::from)
    }

    override suspend fun getSavedRecipes(usersId: String, sortType: SortType): List<RecipeModel> {
        return supabaseDataSource.getSavedRecipes(usersId, sortType).map(RecipeModel::from)
    }

    override suspend fun saveRecipe(response: RecipeResponse): RecipeModel {
        return RecipeModel.from(supabaseDataSource.saveRecipe(response))
    }

    override suspend fun deleteSavedRecipe(response: RecipeResponse): RecipeModel {
        return RecipeModel.from(supabaseDataSource.deleteSavedRecipe(response))
    }
}