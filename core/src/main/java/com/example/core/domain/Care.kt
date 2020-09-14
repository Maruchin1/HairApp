package com.example.core.domain

import java.time.LocalDate

sealed class Care(val methodName: String) {

    val date: LocalDate = LocalDate.now()

    abstract val before: List<CareProduct.Before>
    abstract val main: List<CareProduct.Main>
    abstract val after: List<CareProduct.After>

    data class OMO(
        override val before: MutableList<CareProduct.Before> = mutableListOf(),
        override val main: List<CareProduct.Main> = listOf(
            CareProduct.Main.Conditioner(null),
            CareProduct.Main.Shampoo(null),
            CareProduct.Main.Conditioner(null)
        ),
        override val after: MutableList<CareProduct.After> = mutableListOf()
    ) : Care(methodName = "OMO")

    data class CG(
        override val before: MutableList<CareProduct.Before> = mutableListOf(),
        override val main: List<CareProduct.Main> = listOf(
            CareProduct.Main.Conditioner(null),
            CareProduct.Main.Conditioner(null)
        ),
        override val after: MutableList<CareProduct.After> = mutableListOf()
    ) : Care(methodName = "CG")

    data class Custom(
        override val before: MutableList<CareProduct.Before> = mutableListOf(),
        override val main: MutableList<CareProduct.Main> = mutableListOf(),
        override val after: MutableList<CareProduct.After> = mutableListOf()
    ) : Care("Własna")
}