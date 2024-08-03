package id.nisyafawwaz.nyampur.android.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import id.nisyafawwaz.nyampur.domain.models.ResultState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> StateFlow<ResultState<T>>.observeLiveData(
    lifecycleOwner: LifecycleOwner,
    onLoading: () -> Unit,
    onSuccess: (T) -> Unit,
    onFailure: (Exception) -> Unit,
    onEmpty: (() -> Unit)? = null,
    onIdle: (() -> Unit)? = null
) {
    lifecycleOwner.lifecycleScope.launch {
        collectLatest {
            when (it) {
                ResultState.Idle -> onIdle?.invoke()
                is ResultState.Error -> onFailure.invoke(it.error)
                ResultState.Loading -> onLoading.invoke()
                is ResultState.Success -> onSuccess.invoke(it.data)
                ResultState.Empty -> onEmpty?.invoke()
            }
        }
    }
}
