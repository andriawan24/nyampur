package id.nisyafawwaz.nyampur.android.utils.extensions

import android.content.res.Resources

inline val Int.dp get() = (this / Resources.getSystem().displayMetrics.density).toInt()
inline val Int.px get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.convertToReadableText(): String {
    return when {
        this.toInt() < 60 -> "$this.toInt() second${if (this.toInt() != 1) "s" else ""}"
        this.toInt() < 3600 -> "${this.toInt() / 60} minute${if (this.toInt() / 60 != 1) "s" else ""}"
        this.toInt() < 86400 -> "${this.toInt() / 3600} hour${if (this.toInt() / 3600 != 1) "s" else ""}"
        else -> "${this.toInt() / 86400} day${if (this.toInt() / 86400 != 1) "s" else ""}"
    }
}
