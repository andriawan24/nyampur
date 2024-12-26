package id.nisyafawwaz.nyampur.android.utils.logs

import android.util.Log

fun debug(message: () -> String) {
    Log.d(null, message.invoke())
}
