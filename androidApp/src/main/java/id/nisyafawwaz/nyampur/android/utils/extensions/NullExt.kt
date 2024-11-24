package id.nisyafawwaz.nyampur.android.utils.extensions

fun <T> T?.orDefault(defaultValue: T): T {
    return this ?: defaultValue
}
