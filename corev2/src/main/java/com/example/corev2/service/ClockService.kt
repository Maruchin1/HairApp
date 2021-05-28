package com.example.corev2.service

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

class ClockService {

    fun getNow() = LocalDate.now()

    fun daysBetween(firstDate: LocalDate, secondDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(firstDate, secondDate)
    }

    fun absoluteDaysBetween(firstDate: LocalDate, secondDate: LocalDate): Long {
        return daysBetween(firstDate, secondDate).absoluteValue
    }

    fun isFromLastDays(date: LocalDate, lastDaysLimit: Long): Boolean {
        val daysFromToday = daysBetween(getNow(), date)
        return daysFromToday <= lastDaysLimit
    }

    fun daysUntilToday(date: LocalDate): Long {
        val now = getNow()
        if (date.isAfter(now)) {
            throw IllegalArgumentException("Date has to be before or equal current date: $now")
        }
        return daysBetween(date, now)
    }
}