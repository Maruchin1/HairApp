package com.example.corev2.entities

data class PehBalance(
    val proteins: Double,
    val emollients: Double,
    val humectants: Double
) {

    val isEmpty: Boolean
        get() = arrayOf(proteins, emollients, humectants).all { it == 0.0 }

    companion object {

        fun fromProducts(products: List<Product>): PehBalance {
            val numOfProteins = countIngredient(products) { it.proteins }
            val numOfEmollients = countIngredient(products) { it.emollients }
            val numOfHumectants = countIngredient(products) { it.humectants }
            val numOfAllIngredients = arrayOf(numOfProteins, numOfEmollients, numOfHumectants).sum()
            return if (numOfAllIngredients == 0.0) {
                PehBalance(
                    proteins = 0.0,
                    emollients = 0.0,
                    humectants = 0.0
                )
            } else {
                PehBalance(
                    proteins = numOfProteins / numOfAllIngredients,
                    emollients = numOfEmollients / numOfAllIngredients,
                    humectants = numOfHumectants / numOfAllIngredients,
                )
            }
        }

        private fun countIngredient(
            products: List<Product>,
            selectIngredient: (CompositionOfIngredients) -> Boolean
        ): Double {
            return products
                .count { selectIngredient(it.compositionOfIngredients) }
                .toDouble()
        }
    }
}