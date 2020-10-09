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
                CareStep(Application.Type.CONDITIONER, 0),
                CareStep(Application.Type.SHAMPOO, 1),
                CareStep(Application.Type.CONDITIONER, 2)
            )
        },
        CG {
            override fun makeSteps(): List<CareStep> = listOf(
                CareStep(Application.Type.CONDITIONER, 0),
                CareStep(Application.Type.CONDITIONER, 1)
            )
        },
        CUSTOM {
            override fun makeSteps(): List<CareStep> = listOf()
        };

        abstract fun makeSteps(): List<CareStep>

    }
}