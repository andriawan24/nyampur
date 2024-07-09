package id.nisyafawwaz.nyampur.android.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    var view = currentFocus
    if (view == null) {
        view = View(this)
    }
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.getCompatColor(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.getCompatColorList(@ColorRes color: Int): ColorStateList {
    return ContextCompat.getColorStateList(this, color)!!
}