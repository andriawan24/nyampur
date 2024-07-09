package id.nisyafawwaz.nyampur.android.utils.extensions

import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputEditText.doAfterTextChanged(onTextChanged: (String) -> Unit) {
    doAfterTextChanged {
        onTextChanged.invoke(it.toString())
    }
}

fun TextInputEditText.getValue(): String = this.text.toString()

fun TextInputLayout.removeExtraPaddingError() {
    viewTreeObserver.addOnGlobalLayoutListener {
        if (childCount > 1) {
            getChildAt(1)?.let {
                it.setPaddingLeft(0)
                it.setPaddingTop(4)
            }
        }
    }
}