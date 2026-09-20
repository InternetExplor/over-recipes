package com.wtduyuwnt.overrecipes.util

fun formatAmount(value: Double): String {
    val rounded = Math.round(value * 10.0) / 10.0
    return if (rounded % 1.0 == 0.0) rounded.toInt().toString()
    else rounded.toString().replace('.', ',')
}

fun formatMinutes(minutes: Int): String = when {
    minutes < 60 -> "$minutes мин"
    minutes % 60 == 0 -> "${minutes / 60} ч"
    else -> "${minutes / 60} ч ${minutes % 60} мин"
}

fun formatReviews(count: Int): String =
    if (count >= 1000) "${formatAmount(count / 1000.0)}k отзывов" else "$count отзывов"

fun formatMinutesOrDash(minutes: Int): String =
    if (minutes <= 0) "—" else formatMinutes(minutes)

fun joinMeta(vararg parts: String?): String =
    parts.filterNot { it.isNullOrBlank() }.joinToString(" · ")
