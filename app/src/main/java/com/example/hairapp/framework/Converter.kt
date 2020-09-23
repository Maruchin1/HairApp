package com.example.hairapp.framework

import android.net.Uri
import com.example.core.domain.Care
import com.example.core.domain.ProductApplication
import com.example.core.domain.ProductsProportion
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

object Converter {

    // ProductApplication.Type

    @JvmStatic
    fun productApplicationType(data: ProductApplication.Type?): String? {
        return when (data) {
            ProductApplication.Type.CONDITIONER -> "Odżywka"
            ProductApplication.Type.SHAMPOO -> "Szampon"
            ProductApplication.Type.OTHER -> "Inny"
            else -> null
        }
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
        val formatter = DateTimeFormatter.ofPattern("dd")
        return date?.format(formatter)
    }

    @JvmStatic
    fun shortMonth(date: LocalDate?): String? {
        return date?.month?.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    }

    // Care.Type

    private const val OMO = "OMO"
    private const val CG = "CG"
    private const val CUSTOM = "Własna"

    @JvmStatic
    fun careType(type: Care.Type?): String {
        if (type == null)
            return ""
        return when (type) {
            Care.Type.OMO -> "OMO"
            Care.Type.CG -> "CG"
            Care.Type.CUSTOM -> "Własna"
        }
    }

    @JvmStatic
    fun inverseCareType(type: String?): Care.Type? {
        return when (type) {
            OMO -> Care.Type.OMO
            CG -> Care.Type.CG
            CUSTOM -> Care.Type.CUSTOM
            else -> null
        }
    }

    // ProductsProportion

    @JvmStatic
    fun productsProportion(care: Care?): ProductsProportion? {
        return care?.let { ProductsProportion(it.steps) }
    }


}