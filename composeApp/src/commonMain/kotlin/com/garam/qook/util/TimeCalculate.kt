package com.garam.qook.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Instant

fun getRelativeTimeTextEn(targetEpochMillis: Long): String {
    val now = Clock.System.now()
    val target = Instant.fromEpochSeconds(targetEpochMillis)
    val diff: Duration = now - target
    return when {
        // Less than 1 minute
        diff.inWholeSeconds < 60 -> "Just now"

        // Less than 1 hour
        diff.inWholeMinutes < 60 -> {
            val mins = diff.inWholeMinutes
            if (mins == 1L) "1 minute ago" else "$mins minutes ago"
        }

        // Less than 24 hours
        diff.inWholeHours < 24 -> {
            val hours = diff.inWholeHours
            if (hours == 1L) "1 hour ago" else "$hours hours ago"
        }

        // Less than 7 days
        diff.inWholeDays < 7 -> {
            val days = diff.inWholeDays
            if (days == 1L) "1 day ago" else "$days days ago"
        }

        // 7 days or more: yyyy-MM-dd
        else -> {
            val dateTime = target.toLocalDateTime(TimeZone.currentSystemDefault())
            val year = dateTime.year
            val month = dateTime.month.number.toString().padStart(2, '0')
            val day = dateTime.day.toString().padStart(2, '0')
            "$year-$month-$day"
        }
    }
}