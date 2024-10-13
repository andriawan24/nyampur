package id.nisyafawwaz.nyampur.android.utils.extensions

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun View.onClick(onClick: () -> Unit) {
    setOnClickListener { onClick.invoke() }
}

fun View.onClickWithThrottle(delay: Long = 1000L, onClick: () -> Unit) {
    var job: Job? = null

    setOnClickListener {
        if (job?.isActive == true) {
            return@setOnClickListener
        }

        job = CoroutineScope(Dispatchers.Main).launch {
            onClick.invoke()
            disable()
            delay(delay)
            enable()
        }
    }
}

fun ViewGroup.setStatusBarInset() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
}

fun ViewGroup.setNavigationBarInset() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
        v.updateLayoutParams<MarginLayoutParams> {
            leftMargin = insets.left
            rightMargin = insets.right
            bottomMargin = insets.bottom
        }
        WindowInsetsCompat.CONSUMED
    }
}

fun View.setPaddingLeft(size: Int) {
    setPadding(size, paddingTop, paddingEnd, paddingBottom)
}

fun View.setPaddingTop(size: Int) {
    setPadding(paddingStart, size, paddingEnd, paddingBottom)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

