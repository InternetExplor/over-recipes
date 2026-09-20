package com.wtduyuwnt.overrecipes.util

private val YOUTUBE_ID = Regex("[A-Za-z0-9_-]{11}")

private val YOUTUBE_PATTERNS = listOf(
    Regex("youtu\\.be/([A-Za-z0-9_-]{11})"),
    Regex("youtube\\.com/watch\\?.*v=([A-Za-z0-9_-]{11})"),
    Regex("youtube\\.com/embed/([A-Za-z0-9_-]{11})"),
    Regex("youtube\\.com/shorts/([A-Za-z0-9_-]{11})"),
    Regex("youtube\\.com/v/([A-Za-z0-9_-]{11})")
)

private val DIRECT_EXTENSIONS = listOf(".mp4", ".m3u8", ".webm", ".mkv", ".mov", ".mpd")

object VideoUrls {

    fun youTubeId(raw: String): String? {
        val value = raw.trim()
        if (value.isEmpty()) return null
        YOUTUBE_PATTERNS.forEach { pattern ->
            pattern.find(value)?.let { return it.groupValues[1] }
        }
        return if (!value.contains('/') && YOUTUBE_ID.matches(value)) value else null
    }

    fun isDirectMedia(raw: String): Boolean {
        val path = raw.substringBefore('?').lowercase()
        return DIRECT_EXTENSIONS.any { path.endsWith(it) }
    }

    fun isPlayable(raw: String?): Boolean {
        if (raw.isNullOrBlank()) return false
        return youTubeId(raw) != null || isDirectMedia(raw)
    }
}
