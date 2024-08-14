package id.nisyafawwaz.nyampur.domain.models

import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse

data class RecipeModel(
    val imageUrl: String,
    val cookTime: Int,
    val title: String,
    val level: String,
    val usersId: String
) {
    companion object {
        fun from(response: RecipeResponse?): RecipeModel {
            return RecipeModel(
                imageUrl = response?.imageUrl.orEmpty(),
                cookTime = response?.cookTime ?: 0,
                title = response?.title.orEmpty(),
                level = response?.level.orEmpty(),
                usersId = response?.usersId.orEmpty()
            )
        }
    }
}