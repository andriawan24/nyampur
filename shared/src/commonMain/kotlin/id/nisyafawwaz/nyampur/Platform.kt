package id.nisyafawwaz.nyampur

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform