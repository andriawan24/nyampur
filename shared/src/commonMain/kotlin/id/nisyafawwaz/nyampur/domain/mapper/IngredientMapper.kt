package id.nisyafawwaz.nyampur.domain.mapper

import id.nisyafawwaz.nyampur.data.models.responses.IngredientResponse
import id.nisyafawwaz.nyampur.domain.models.IngredientModel

fun IngredientResponse.toDomain() = IngredientModel(
    name = this.name
)
