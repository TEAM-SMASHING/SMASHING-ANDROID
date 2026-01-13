package com.smashing.app.core.extension

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun LocalDateTime.toFriendlyString(): String {
    val now = LocalDateTime.now()

    val targetDate = this.toLocalDate()
    val nowDate = now.toLocalDate()

    if (targetDate.isEqual(nowDate)) {
        return "오늘"
    }

    val daysDiff = ChronoUnit.DAYS.between(targetDate, nowDate)
    val monthsDiff = ChronoUnit.MONTHS.between(targetDate, nowDate)
    val yearsDiff = ChronoUnit.YEARS.between(targetDate, nowDate)

    return when {
        daysDiff < 30 -> "${daysDiff}일 전"
        monthsDiff < 12 -> "${monthsDiff}개월 전"
        else -> "${yearsDiff}년 전"
    }
}
