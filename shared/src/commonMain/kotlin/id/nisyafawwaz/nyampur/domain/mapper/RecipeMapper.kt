package id.nisyafawwaz.nyampur.domain.mapper

import id.nisyafawwaz.nyampur.data.models.responses.RecipeResponse
import id.nisyafawwaz.nyampur.domain.models.RecipeModel

fun RecipeModel.toRequest(userId: String) = RecipeResponse(
    imageUrl = this.imageUrl,
    cookTime = this.cookTime,
    title = this.title,
    level = this.level,
    usersId = userId
)