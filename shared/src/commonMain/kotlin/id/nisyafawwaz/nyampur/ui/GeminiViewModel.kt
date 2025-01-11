package id.nisyafawwaz.nyampur.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.Bitmap
import dev.shreyaspatil.ai.client.generativeai.type.BlockThreshold
import dev.shreyaspatil.ai.client.generativeai.type.HarmCategory
import dev.shreyaspatil.ai.client.generativeai.type.SafetySetting
import dev.shreyaspatil.ai.client.generativeai.type.content
import dev.shreyaspatil.ai.client.generativeai.type.generationConfig
import id.nisyafawwaz.nyampur.BuildKonfig
import id.nisyafawwaz.nyampur.data.models.responses.IngredientListResponse
import id.nisyafawwaz.nyampur.domain.mapper.toDomain
import id.nisyafawwaz.nyampur.domain.models.IngredientModel
import id.nisyafawwaz.nyampur.domain.models.ResultState
import id.nisyafawwaz.nyampur.utils.GenerationConstants
import id.nisyafawwaz.nyampur.utils.cleanUpJsonFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class GeminiViewModel : ViewModel() {
    private val _ingredientsResult = MutableStateFlow<ResultState<List<IngredientModel>>>(ResultState.Idle)
    val ingredientsResult = _ingredientsResult.asStateFlow()

    private val model = GenerativeModel(
        modelName = BuildKonfig.GEMINI_MODEL_NAME,
        apiKey = BuildKonfig.GEMINI_API_KEY,
        generationConfig = generationConfig {
            temperature = 0.15f
            topK = 32
            topP = 1f
            maxOutputTokens = 2046
        },
        safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
        )
    )

    fun extractIngredients(images: List<ByteArray>) {
        viewModelScope.launch {
            _ingredientsResult.emit(ResultState.Loading)
            try {
                val response = model.generateContent(
                    content {
                        images.forEach { image -> image(image) }
                        text(GenerationConstants.INGREDIENT_EXTRACT_COMMAND)
                    }
                )
                val text = response.text.cleanUpJsonFormat()
                val data = Json.Default.decodeFromString<IngredientListResponse>(text)
                val ingredients = data.data.map { response -> response.toDomain() }
                _ingredientsResult.emit(ResultState.Success(ingredients))
            } catch (e: Exception) {
                _ingredientsResult.emit(ResultState.Error(e))
            }
        }
    }
}
