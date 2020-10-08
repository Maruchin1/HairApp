package com.example.core.domain

import java.time.LocalDate

data class Care(
    val id: Int,
    val type: Type,
    var date: LocalDate,
    var photos: List<String>,
    var steps: List<CareProduct>
) {

    enum class Type {
        OMO {
            override fun makeSteps(): List<CareProduct> = listOf(
                CareProduct(Application.Type.CONDITIONER),
                CareProduct(Application.Type.SHAMPOO),
                CareProduct(Application.Type.CONDITIONER)
            )
        },
        CG {
            override fun makeSteps(): List<CareProduct> = listOf(
                CareProduct(Application.Type.CONDITIONER),
                CareProduct(Application.Type.CONDITIONER)
            )
        },
        CUSTOM {
            override fun makeSteps(): List<CareProduct> = listOf()
        };

        abstract fun makeSteps(): List<CareProduct>

    }
}