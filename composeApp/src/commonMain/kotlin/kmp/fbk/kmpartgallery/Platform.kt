package kmp.fbk.kmpartgallery

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform