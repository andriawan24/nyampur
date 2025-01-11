package id.nisyafawwaz.nyampur.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientListResponse(
    @SerialName("data")
    val data: List<IngredientResponse>
)

@Serializable
data class IngredientResponse(
    @SerialName("name")
    val name: String
)
