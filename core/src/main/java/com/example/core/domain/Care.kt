package com.example.core.domain

import java.time.LocalDate

data class Care(
    val id: Int,
    val type: Type,
    var date: LocalDate,
    var steps: List<CareProduct>
) {

    enum class Type {
        OMO {
            override fun makeSteps(): List<CareProduct> = listOf(
                CareProduct(ProductApplication.Type.CONDITIONER),
                CareProduct(ProductApplication.Type.SHAMPOO),
                CareProduct(ProductApplication.Type.CONDITIONER)
            )
        },
        CG {
            override fun makeSteps(): List<CareProduct> = listOf(
                CareProduct(ProductApplication.Type.CONDITIONER),
                CareProduct(ProductApplication.Type.CONDITIONER)
            )
        },
        CUSTOM {
            override fun makeSteps(): List<CareProduct> = listOf()
        };

        abstract fun makeSteps(): List<CareProduct>
    }
}