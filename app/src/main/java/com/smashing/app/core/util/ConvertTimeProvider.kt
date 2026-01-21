package com.smashing.app.core.util

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

object ConvertTimeProvider {
    fun convertLocalDateTimeToTime(time: String): String {
        val createdInstant = parseToInstant(time) ?: return ""
        val nowInstant = Instant.now().minus(9, ChronoUnit.HOURS)

        val createdUtc = ZonedDateTime.ofInstant(createdInstant, ZoneId.of("UTC"))
        val nowUtc = ZonedDateTime.ofInstant(nowInstant, ZoneId.of("UTC"))

        val duration = Duration.between(createdInstant, nowInstant)
        val minutes = duration.toMinutes()
        val hours = duration.toHours()
        val days = duration.toDays()
        val weeks = days / 7
        val months = ChronoUnit.MONTHS.between(createdUtc, nowUtc)
        val years = ChronoUnit.YEARS.between(createdUtc, nowUtc)

        val convertedTime = when {
            minutes < 1 -> "방금 전"
            minutes < 60 -> "${minutes}분 전"
            hours < 24 -> "${hours}시간 전"
            days < 7 -> "${days}일 전"
            weeks < 4 -> "${weeks}주 전"
            months < 12 -> "${months}달 전"
            else -> "${years}년 전"
        }

        return convertedTime
    }

    private fun parseToInstant(time: String): Instant? =
        runCatching {
            OffsetDateTime.parse(time).toInstant()
        }
            .recoverCatching {
                LocalDateTime.parse(time).atZone(ZoneId.of("UTC")).toInstant()
            }
            .getOrNull()
}
