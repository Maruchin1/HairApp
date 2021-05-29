package com.example.corev2.service

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

class ClockService {

    fun getNow() = LocalDateTime.now()

    fun daysBetween(firstDate: LocalDateTime, secondDate: LocalDateTime): Long {
        return ChronoUnit.DAYS.between(firstDate, secondDate)
    }

    fun absoluteDaysBetween(firstDate: LocalDateTime, secondDate: LocalDateTime): Long {
        return daysBetween(firstDate, secondDate).absoluteValue
    }

    fun isFromLastDays(date: LocalDateTime, lastDaysLimit: Long): Boolean {
        val daysFromToday = daysBetween(getNow(), date)
        return daysFromToday <= lastDaysLimit
    }

    fun daysUntilToday(date: LocalDateTime): Long {
        val now = getNow()
        if (date.isAfter(now)) {
            throw IllegalArgumentException("Date has to be before or equal current date: $now")
        }
        return daysBetween(date, now)
    }
}