package com.garam.qook.util

fun isYouTubeUrl(url: String): Boolean {
    // 유튜브 전용 정규표현식
    val youtubeRegex = "^(https?://)?(www\\.|m\\.)?(youtube\\.com|youtu\\.be)/(watch\\?v=|embed/|v/|shorts/)?([a-zA-Z0-9_-]{11})(\\S+)?$"

    return Regex(youtubeRegex).matches(url)
}