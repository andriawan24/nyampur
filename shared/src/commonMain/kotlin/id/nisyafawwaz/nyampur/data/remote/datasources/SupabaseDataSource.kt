package id.nisyafawwaz.nyampur.data.remote.datasources

import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from

class SupabaseDataSource(private val client: SupabaseClient) {

    suspend fun sendEmailSignInOtp(email: String) {
        client.auth.signInWith(provider = OTP) { this.email = email }
    }

    suspend fun validateEmailOtp(token: String, email: String) {
        client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, token)
    }

    suspend fun retrieveUserSession(): UserInfo? {
        val user = client.auth.sessionManager.loadSession()
        return user?.user
    }

    suspend fun getSavedRecipes(userId: String): List<RecipeResponse> {
        return client.from(RecipeResponse.TABLE_NAME)
            .select {
                filter {
                    RecipeResponse::usersId eq userId
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