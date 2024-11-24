package id.nisyafawwaz.nyampur.android.utils.extensions

import android.content.res.Resources

inline val Int.dp get() = (this / Resources.getSystem().displayMetrics.density).toInt()
inline val Int.px get() = (this * Resources.getSystem().displayMetrics.density).toInt()
