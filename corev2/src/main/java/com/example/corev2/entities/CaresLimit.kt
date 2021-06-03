package com.example.corev2.entities

enum class CaresLimit(val daysLimit: Int) {
    LAST_WEEK(7),
    LAST_TWO_WEEKS(14),
    LAST_THREE_WEEKS(21),
    LAST_MONTH(30),
    LAST_TWO_MONTH(60),
    LAST_THREE_MONTH(90),
    ALL(Int.MAX_VALUE),
}