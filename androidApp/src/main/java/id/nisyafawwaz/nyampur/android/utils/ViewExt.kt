package id.nisyafawwaz.nyampur.android.utils

import android.view.View

fun View.onClick(onClick: () -> Unit) {
    this.setOnClickListener { onClick.invoke() }
}