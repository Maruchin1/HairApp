package com.example.core.domain

import java.time.LocalDate

data class Care(
    val date: LocalDate = LocalDate.now(),
    val before: MutableList<CareProduct.Extra>,
    val main: List<CareProduct.Main>,
    val after: MutableList<CareProduct.Extra>,
)