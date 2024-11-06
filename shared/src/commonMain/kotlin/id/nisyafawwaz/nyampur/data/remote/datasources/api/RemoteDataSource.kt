package id.nisyafawwaz.nyampur.data.remote.datasources.api

import id.nisyafawwaz.nyampur.data.models.requests.GetRecipeRequest
import id.nisyafawwaz.nyampur.data.models.responses.BaseResponse
import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class RemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getRecipes(type: String, page: Int): BaseResponse<List<RecipeResponse>> {
        return httpClient.get(GetRecipeRequest(type, page.toString())).body()
    }
}