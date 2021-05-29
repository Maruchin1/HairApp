package com.example.corev2.service

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.*

fun LocalDateTime.daysBetween(otherDate: LocalDateTime): Long {
    return ChronoUnit.DAYS.between(this, otherDate)
}

fun LocalDateTime.formatDayOfWeekAndMonth(): String {
    return format(DateTimeFormatter.ofPattern("EE dd MMMM"))
}

fun LocalDateTime.formatDayOfMonth(): String {
    return format(DateTimeFormatter.ofPattern("dd"))
}

fun LocalDateTime.formatShortMonth(): String {
    return month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
}