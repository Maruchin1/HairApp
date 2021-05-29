package com.example.corev2.service

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.*

fun LocalDate.daysBetween(otherDate: LocalDate): Long {
    return ChronoUnit.DAYS.between(this, otherDate)
}

fun LocalDate.formatDayOfWeekAndMonth(): String {
    return format(DateTimeFormatter.ofPattern("EE dd MMMM"))
}

fun LocalDate.formatDayOfMonth(): String {
    return format(DateTimeFormatter.ofPattern("dd"))
}

fun LocalDate.formatShortMonth(): String {
    return month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
}