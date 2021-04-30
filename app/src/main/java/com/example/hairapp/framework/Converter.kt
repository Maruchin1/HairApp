package com.example.hairapp.framework

import android.net.Uri
import com.example.core.domain.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

object Converter {

    // Product.Application

    private const val MILD_SHAMPOO = "Łagodny szampon"
    private const val MEDIUM_SHAMPOO = "Średni szampon"
    private const val STRONG_SHAMPOO = "Mocny szampon"
    private const val CONDITIONER = "Odżywka"
    private const val CREAM = "Krem"
    private const val MASK = "Maska"
    private const val LEAVE_IN_CONDITIONER = "Odżywka b/s"
    private const val OIL = "Olej"
    private const val FOAM = "Pianka"
    private const val SERUM = "Serum"
    private const val GEL = "Żel"
    private const val OTHER = "Inny"

    @JvmStatic
    fun productApplication(data: Product.Application?): String? {
        return when (data) {
            Product.Application.MILD_SHAMPOO -> MILD_SHAMPOO
            Product.Application.MEDIUM_SHAMPOO -> MEDIUM_SHAMPOO
            Product.Application.STRONG_SHAMPOO -> STRONG_SHAMPOO
            Product.Application.CONDITIONER -> CONDITIONER
            Product.Application.CREAM -> CREAM
            Product.Application.MASK -> MASK
            Product.Application.LEAVE_IN_CONDITIONER -> LEAVE_IN_CONDITIONER
            Product.Application.OIL -> OIL
            Product.Application.FOAM -> FOAM
            Product.Application.SERUM -> SERUM
            Product.Application.GEL -> GEL
            Product.Application.OTHER -> OTHER
            null -> null
        }
    }

    @JvmStatic
    fun inverseProductApplication(data: String?): Product.Application? {
        return when (data) {
            MILD_SHAMPOO -> Product.Application.MILD_SHAMPOO
            MEDIUM_SHAMPOO -> Product.Application.MEDIUM_SHAMPOO
            STRONG_SHAMPOO -> Product.Application.STRONG_SHAMPOO
            CONDITIONER -> Product.Application.CONDITIONER
            CREAM -> Product.Application.CREAM
            MASK -> Product.Application.MASK
            LEAVE_IN_CONDITIONER -> Product.Application.LEAVE_IN_CONDITIONER
            OIL -> Product.Application.OIL
            FOAM -> Product.Application.FOAM
            SERUM -> Product.Application.SERUM
            GEL -> Product.Application.GEL
            OTHER -> Product.Application.OTHER
            else -> null
        }
    }

    // CareStep.Type

    @JvmStatic
    fun careStepType(data: CareStep.Type?): String? = when (data) {
        CareStep.Type.CONDITIONER -> "Odżywka"
        CareStep.Type.SHAMPOO -> "Szampon"
        CareStep.Type.OIL -> "Olej"
        CareStep.Type.EMULSIFIER -> "Emulgator"
        CareStep.Type.STYLIZER -> "Stylizator"
        CareStep.Type.OTHER -> "Inne"
        null -> null
    }

    // CaresForBalance

    @JvmStatic
    fun caresForBalance(data: CaresLimit?): String? = when (data) {
        CaresLimit.LAST_WEEK -> "Ostatni tydzień"
        CaresLimit.LAST_TWO_WEEKS -> "Ostatnie dwa tygodnie"
        CaresLimit.LAST_THREE_WEEKS -> "Ostatnie trzy tygodnie"
        CaresLimit.LAST_MONTH -> "Ostatni miesiąc"
        CaresLimit.LAST_TWO_MONTH -> "Ostatnie dwa miesiące"
        CaresLimit.LAST_THREE_MONTH -> "Ostatenie trzy miesiące"
        CaresLimit.ALL -> "Wszystkie pielęgnacje"
        null -> null
    }

    // Photo

    @JvmStatic
    fun photo(photoData: String?): Uri? {
        return photoData?.let { Uri.parse(it) }
    }

    // Date

    private val fullDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    @JvmStatic
    fun date(date: LocalDate?): String? {
        return date?.format(fullDateFormatter)
    }

    @JvmStatic
    fun inverseDate(date: String?): LocalDate? {
        return date?.let { LocalDate.parse(it, fullDateFormatter) }
    }

    @JvmStatic
    fun dayOfMonth(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ofPattern("dd"))
    }

    @JvmStatic
    fun shortMonth(date: LocalDate?): String? {
        return date?.month?.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    }

    @JvmStatic
    fun dayAndMonth(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ofPattern("dd MMMM"))
    }

    @JvmStatic
    fun dayOfWeekAndMonth(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ofPattern("EE dd MMMM"))
    }

    // ProductsProportion

    @JvmStatic
    fun pehBalance(care: Care?): PehBalance? {
        return care?.let { PehBalance.fromSteps(it.steps) }
    }

    // CareNotes
    @JvmStatic
    fun careNotes(notes: String?): String {
        return if (notes.isNullOrEmpty() || notes.isBlank()) {
            "Zapisz swoje notatki, uwagi, obserwacje ..."
        } else {
            notes
        }
    }

}