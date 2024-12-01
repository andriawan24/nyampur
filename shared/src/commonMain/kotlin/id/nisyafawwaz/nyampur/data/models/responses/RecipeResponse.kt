package id.nisyafawwaz.nyampur.data.models.responses

import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("cook_time")
    val cookTime: Int? = null,
    val title: String? = null,
    val level: String? = null,
    val user: UserInfo? = null,
    @SerialName("users_id")
    val usersId: String? = null,
) {
    companion object {
        const val TABLE_NAME = "saved_recipes"
    }
}
