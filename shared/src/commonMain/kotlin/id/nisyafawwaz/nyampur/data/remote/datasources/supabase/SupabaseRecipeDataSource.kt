package id.nisyafawwaz.nyampur.data.remote.datasources.supabase

import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import id.nisyafawwaz.nyampur.utils.enums.SortType
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class SupabaseRecipeDataSource(private val client: SupabaseClient) {

    suspend fun getSavedRecipes(userId: String, sortType: SortType): List<RecipeResponse> {
        return client.from(RecipeResponse.TABLE_NAME)
            .select {
                filter {
                    RecipeResponse::usersId eq userId
                }
                when (sortType) {
                    SortType.RECENTLY -> order("created_at", Order.DESCENDING)
                    SortType.LEVEL -> order("level", Order.ASCENDING)
                    SortType.MINUTES -> order("cook_time", Order.ASCENDING)
                    SortType.INCREASING -> order("title", Order.ASCENDING)
                    SortType.DECREASING -> order("title", Order.DESCENDING)
                }
            }
            .decodeList<RecipeResponse>()
    }

    suspend fun saveRecipe(recipeResponse: RecipeResponse): RecipeResponse {
        client.from(RecipeResponse.TABLE_NAME).insert(recipeResponse)
        return recipeResponse
    }

    suspend fun deleteSavedRecipe(recipeResponse: RecipeResponse): RecipeResponse {
        client.from(RecipeResponse.TABLE_NAME).delete {
            filter {
                RecipeResponse::title eq recipeResponse.title
            }
        }
        return recipeResponse
    }
}