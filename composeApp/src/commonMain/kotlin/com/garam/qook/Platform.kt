package com.garam.qook

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform