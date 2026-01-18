package com.smashing.app.core.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

object ConvertTimeProvider {
    fun convertLocalDateTimeToTime(localDateTime: LocalDateTime): String {

        val createdTime = localDateTime
        val now = LocalDateTime.now(ZoneId.systemDefault())

        val minutes = ChronoUnit.MINUTES.between(createdTime, now)
        val hours = ChronoUnit.HOURS.between(createdTime, now)
        val days = ChronoUnit.DAYS.between(createdTime, now)
        val weeks = ChronoUnit.WEEKS.between(createdTime, now)
        val months = ChronoUnit.MONTHS.between(createdTime, now)
        val years = ChronoUnit.YEARS.between(createdTime, now)

        val convertedTime = when {
            minutes < 1 -> ""
            minutes < 60 -> "${minutes}분 전"
            hours < 24 -> "${hours}시간 전"
            days < 7 -> "${days}일 전"
            weeks < 4 -> "${weeks}주 전"
            months < 12 -> "${months}달 전"
            else -> "${years}년 전"
        }

        return convertedTime
    }
}
