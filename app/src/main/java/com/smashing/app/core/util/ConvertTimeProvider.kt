package com.smashing.app.core.util

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object ConvertTimeProvider {
    fun convertLocalDateTimeToTime(time: String): String {
        val createdInstant = parseToInstant(time) ?: return ""
        val nowInstant = Instant.now()

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
    fun calculateNotificationTime(timeString: String): String {
        return runCatching {
            val cleanTime = timeString.replace(" ", "T")

            val localDateTime = LocalDateTime.parse(cleanTime, DateTimeFormatter.ISO_DATE_TIME)

            val createdTime = localDateTime.atZone(ZoneId.of("UTC")).toInstant()
            val now = Instant.now()

            val duration = Duration.between(createdTime, now)
            val minutes = duration.toMinutes()
            val hours = duration.toHours()
            val days = duration.toDays()

            if (minutes < 0) return "방금 전"

            when {
                minutes < 1 -> "방금 전"
                minutes < 60 -> "${minutes}분 전"
                hours < 24 -> "${hours}시간 전"
                days < 7 -> "${days}일 전"
                days < 30 -> "${days / 7}주 전"
                days < 365 -> "${days / 30}달 전"
                else -> "${days / 365}년 전"
            }
        }.getOrElse { "" }
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
