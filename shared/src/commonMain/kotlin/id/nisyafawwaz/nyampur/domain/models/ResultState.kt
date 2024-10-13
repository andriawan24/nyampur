package id.nisyafawwaz.nyampur.domain.models

sealed class ResultState<out T> {
    data object Idle : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
    data object Empty : ResultState<Nothing>()
    class Error(val error: Exception) : ResultState<Nothing>()
    class Success<T>(val data: T) : ResultState<T>()
}