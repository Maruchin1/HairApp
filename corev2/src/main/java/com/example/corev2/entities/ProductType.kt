package com.example.corev2.entities

import com.example.corev2.R

enum class ProductType(val resId: Int) {
    CONDITIONER(R.string.conditioner),
    SHAMPOO(R.string.shampoo),
    OIL(R.string.oil),
    EMULSIFIER(R.string.emulsifier),
    STYLIZER(R.string.stylizer),
    OTHER(R.string.other)
}