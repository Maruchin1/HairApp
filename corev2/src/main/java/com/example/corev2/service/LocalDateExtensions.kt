package com.example.corev2.service

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun LocalDate.daysBetween(otherDate: LocalDate): Long {
    return ChronoUnit.DAYS.between(this, otherDate)
}