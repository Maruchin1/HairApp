package com.example.core.domain

class ProductsProportion(steps: List<CareProduct>) {

    val humectants: Float
    val emollients: Float
    val proteins: Float

    init {
        val numOfHumectants = steps.count { it.product?.type?.humectants == true }.toFloat()
        val numOfEmollients = steps.count { it.product?.type?.emollients == true }.toFloat()
        val numOfProteins = steps.count { it.product?.type?.proteins == true }.toFloat()

        val numOfAll = numOfHumectants + numOfEmollients + numOfProteins

        if (numOfAll == 0f) {
            humectants = 0f
            emollients = 0f
            proteins = 0f
        } else {
            humectants = numOfHumectants / numOfAll
            emollients = numOfEmollients / numOfAll
            proteins = numOfProteins / numOfAll
        }
    }

    fun isEmpty(): Boolean {
        return humectants == 0f && emollients == 0f && proteins == 0f
    }
}