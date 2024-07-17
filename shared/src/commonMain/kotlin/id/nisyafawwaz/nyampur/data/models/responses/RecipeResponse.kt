package id.nisyafawwaz.nyampur.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("cook_time")
    val cookTime: Int? = null,
    val title: String? = null,
    val level: String? = null
)