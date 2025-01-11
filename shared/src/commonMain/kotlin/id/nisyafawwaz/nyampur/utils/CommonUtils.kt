package id.nisyafawwaz.nyampur.utils

fun emptyString() = ""
fun String?.cleanUpJsonFormat(): String {
    return this.orEmpty().replace("```json", "").replace("```", "")
}
