package com.example.corev2.entities

data class Ingredients(
    val proteins: Boolean = false,
    val emollients: Boolean = false,
    val humectants: Boolean = false,
) {

    val hasIngredients: Boolean
        get() = proteins || emollients || humectants
}