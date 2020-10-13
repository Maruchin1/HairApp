package com.example.core.domain

import java.time.LocalDate

data class DayPhotos(
    val date: LocalDate,
    val photos: List<String>
)