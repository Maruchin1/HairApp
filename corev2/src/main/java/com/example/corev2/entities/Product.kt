package com.example.corev2.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.corev2.R

@Entity
data class Product(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @Embedded
    val compositionOfIngredients: CompositionOfIngredients,

    var name: String,

    var manufacturer: String,

    var applications: Set<Application>,

    var photoData: String?
) {

    enum class Type(val resId: Int, val applications: Array<Application>) {
        CONDITIONER(
            resId = R.string.conditioner,
            applications = arrayOf(
                Application.CONDITIONER
            )
        ),
        SHAMPOO(
            resId = R.string.shampoo,
            applications = arrayOf(
                Application.MILD_SHAMPOO,
                Application.MEDIUM_SHAMPOO,
                Application.STRONG_SHAMPOO
            )
        ),
        OIL(
            resId = R.string.oil,
            applications = arrayOf(
                Application.OIL
            )
        ),
        EMULSIFIER(
            resId = R.string.emulsifier,
            applications = arrayOf(
                Application.CONDITIONER
            )
        ),
        STYLIZER(
            resId = R.string.stylizer,
            applications = arrayOf(
                Application.MILD_SHAMPOO,
                Application.MEDIUM_SHAMPOO,
                Application.STRONG_SHAMPOO,
                Application.CONDITIONER
            )
        ),
        OTHER(
            resId = R.string.other,
            applications = arrayOf(
                Application.CREAM,
                Application.MASK,
                Application.LEAVE_IN_CONDITIONER,
                Application.FOAM,
                Application.SERUM,
                Application.GEL,
                Application.OTHER
            )
        )
    }

    enum class Application {
        MILD_SHAMPOO,
        MEDIUM_SHAMPOO,
        STRONG_SHAMPOO,
        CONDITIONER,
        CREAM,
        MASK,
        LEAVE_IN_CONDITIONER,
        OIL,
        FOAM,
        SERUM,
        GEL,
        OTHER
    }
}