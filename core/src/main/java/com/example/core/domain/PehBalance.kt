package com.example.core.domain

data class PehBalance(
    val humectants: Double,
    val emollients: Double,
    val proteins: Double
) {

    fun isEmpty(): Boolean {
        return humectants == 0.0 && emollients == 0.0 && proteins == 0.0
    }

    companion object {

        fun fromSteps(steps: List<CareStep>): PehBalance {
            val numOfHumectants = steps
                .count { it.product?.composition?.humectants == true }
                .toDouble()
            val numOfEmollients = steps
                .count { it.product?.composition?.emollients == true }
                .toDouble()
            val numOfProteins = steps
                .count { it.product?.composition?.proteins == true }
                .toDouble()

            val numOfAll = numOfHumectants + numOfEmollients + numOfProteins

            return if (numOfAll == 0.0) {
                PehBalance(
                    humectants = 0.0,
                    emollients = 0.0,
                    proteins = 0.0
                )
            } else {
                PehBalance(
                    humectants = numOfHumectants / numOfAll,
                    emollients = numOfEmollients / numOfAll,
                    proteins = numOfProteins / numOfAll
                )
            }
        }
    }
}