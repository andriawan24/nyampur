package id.nisyafawwaz.nyampur.domain.models

import id.nisyafawwaz.nyampur.utils.emptyString

data class IngredientModel(
    val name: String = emptyString(),
    var imagePath: String = emptyString()
)
