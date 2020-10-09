package com.example.core.domain

import java.time.LocalDate

data class Care(
    val id: Int,
    val type: Type,
    var date: LocalDate,
    var photos: List<String>,
    var steps: List<CareStep>
) {

    enum class Type {
        OMO {
            override fun makeSteps(): List<CareStep> = listOf(
                CareStep(Application.Type.CONDITIONER),
                CareStep(Application.Type.SHAMPOO),
                CareStep(Application.Type.CONDITIONER)
            )
        },
        CG {
            override fun makeSteps(): List<CareStep> = listOf(
                CareStep(Application.Type.CONDITIONER),
                CareStep(Application.Type.CONDITIONER)
            )
        },
        CUSTOM {
            override fun makeSteps(): List<CareStep> = listOf()
        };

        abstract fun makeSteps(): List<CareStep>

    }
}