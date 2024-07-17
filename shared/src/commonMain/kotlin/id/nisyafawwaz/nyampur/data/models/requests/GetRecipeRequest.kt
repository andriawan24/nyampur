package id.nisyafawwaz.nyampur.data.models.requests

import io.ktor.resources.Resource

@Resource("/api/v1/recipes")
class GetRecipeRequest(val type: String = "type", val page: String = "page")
