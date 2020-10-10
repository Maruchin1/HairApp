package com.example.core.domain

enum class CareTemplate {
    OMO {
        override fun makeSteps(): List<CareStep> = listOf(
            CareStep(CareStep.Type.CONDITIONER, order = 0),
            CareStep(CareStep.Type.SHAMPOO, order = 1),
            CareStep(CareStep.Type.CONDITIONER, order = 2)
        )
    },
    CG {
        override fun makeSteps(): List<CareStep> = listOf(
            CareStep(CareStep.Type.CONDITIONER, order = 0),
            CareStep(CareStep.Type.CONDITIONER, order = 1)
        )
    };

    abstract fun makeSteps(): List<CareStep>
}