package com.example.core.use_case

import com.example.core.base.UseCase
import com.example.core.domain.Care
import com.example.core.domain.CareProduct
import com.example.core.gateway.CareRepo
import java.time.LocalDate
import javax.inject.Inject

class AddCare @Inject constructor(
    private val careRepo: CareRepo
) : UseCase<AddCare.Input, Unit>() {

    override suspend fun execute(input: Input) {
        val newCare = when (input) {
            is Input.OMO -> makeOMO(input)
            is Input.CG -> makeCG(input)
            is Input.Custom -> makeCustom(input)
        }
        newCare.steps = input.steps
        saveCare(newCare)
    }

    private fun makeOMO(input: Input.OMO): Care {
        return Care.OMO(id = 0, input.date)
    }

    private fun makeCG(input: Input.CG): Care {
        return Care.CG(id = 0, input.date)
    }

    private fun makeCustom(input: Input.Custom): Care {
        return Care.Custom(id = 0, input.date)
    }

    private suspend fun saveCare(care: Care) {
        careRepo.add(care)
    }

    sealed class Input {
        abstract val date: LocalDate
        abstract val steps: List<CareProduct>

        data class OMO(
            override val date: LocalDate,
            override val steps: List<CareProduct>
        ) : Input()

        data class CG(
            override val date: LocalDate,
            override val steps: List<CareProduct>
        ) : Input()

        data class Custom(
            override val date: LocalDate,
            override val steps: List<CareProduct>
        ) : Input()
    }
}