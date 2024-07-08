package id.nisyafawwaz.nyampur.android.utils.extensions

import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.doAfterTextChanged(onTextChanged: (String) -> Unit) {
    doAfterTextChanged {
        onTextChanged.invoke(it.toString())
    }
}

fun TextInputEditText.getValue(): String = this.text.toString()