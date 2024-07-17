package id.nisyafawwaz.nyampur.data.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val meta: MetaResponse,
    val data: T
)

@Serializable
data class MetaResponse(
    val message: String? = null,
    val statusCode: Int? = null
)