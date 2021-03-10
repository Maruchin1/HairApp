package com.example.core.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

class AppClock {

    fun now(): LocalDate {
        return LocalDate.now()
    }

    fun daysBetween(firstDate: LocalDate, secondDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(firstDate, secondDate)
    }

    fun absoluteDaysBetween(firstDate: LocalDate, secondDate: LocalDate): Long {
        return daysBetween(firstDate, secondDate).absoluteValue
    }

    fun isFromLastDays(date: LocalDate, lastDaysLimit: Long): Boolean {
        val daysFromToday = daysBetween(now(), date)
        return daysFromToday <= lastDaysLimit
    }

    fun daysUntilToday(date: LocalDate): Long {
        val now = now()
        if (date.isAfter(now)) {
            throw IllegalArgumentException("Date has to be before or equal current date: $now")
        }
        return daysBetween(date, now)
    }
}