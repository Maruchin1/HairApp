package com.example.core.domain

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

data class Care(
    val id: Int,
    val schemaName: String,
    var date: LocalDate,
    var photos: List<String>,
    var steps: List<CareStep>,
    var notes: String
) {

    fun isFromLastDays(numOfDays: Long): Boolean {
        val currentDate = LocalDate.now()
        val daysFromToday = ChronoUnit.DAYS.between(currentDate, date)
        return daysFromToday <= numOfDays
    }

    fun daysFromCare(care: Care): Long {
        return ChronoUnit.DAYS.between(care.date, date).absoluteValue
    }
}