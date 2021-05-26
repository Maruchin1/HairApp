package com.example.corev2.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.corev2.R

@Entity
data class Product(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @Embedded
    val compositionOfIngredients: CompositionOfIngredients = CompositionOfIngredients(),

    var name: String = "",

    var manufacturer: String = "",

    var applications: Set<Application> = setOf(),

    var photoData: String? = null
) {

    fun isValid(): Boolean {
        return name.isNotEmpty()
    }

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
        );
    }

    enum class Application(val resId: Int) {
        MILD_SHAMPOO(R.string.mild_shampoo),
        MEDIUM_SHAMPOO(R.string.medium_shampoo),
        STRONG_SHAMPOO(R.string.strong_shampoo),
        CONDITIONER(R.string.conditioner),
        CREAM(R.string.cream),
        MASK(R.string.mask),
        LEAVE_IN_CONDITIONER(R.string.leave_in_conditioner),
        OIL(R.string.oil),
        FOAM(R.string.foam),
        SERUM(R.string.serum),
        GEL(R.string.gel),
        OTHER(R.string.other)
    }
}