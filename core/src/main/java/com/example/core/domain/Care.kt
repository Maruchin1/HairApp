package com.example.core.domain

import java.time.LocalDate

sealed class Care {
    abstract var date: LocalDate

    abstract var steps: List<CareProduct>

    data class OMO(
        override var date: LocalDate = LocalDate.now(),
        private val firstConditioner: CareProduct = CareProduct(ProductApplication.Type.CONDITIONER),
        private val shampoo: CareProduct = CareProduct(ProductApplication.Type.SHAMPOO),
        private val secondConditioner: CareProduct = CareProduct(ProductApplication.Type.CONDITIONER),
        private var before: List<CareProduct> = listOf(),
        private var after: List<CareProduct> = listOf()
    ) : Care() {

        override var steps: List<CareProduct>
            get() = mutableListOf<CareProduct>().apply {
                addAll(before)
                add(firstConditioner)
                add(shampoo)
                add(secondConditioner)
                addAll(after)
            }
            set(value) {
                val mainProducts = value.filter { it.specificApplicationType != null }
                check(mainProducts.size == 3)

                val firstMain = mainProducts[0]
                val secondMain = mainProducts[1]
                val thirdMain = mainProducts[2]

                check(firstMain.specificApplicationType == ProductApplication.Type.CONDITIONER)
                check(secondMain.specificApplicationType == ProductApplication.Type.SHAMPOO)
                check(thirdMain.specificApplicationType == ProductApplication.Type.CONDITIONER)

                firstConditioner.product = firstMain.product
                shampoo.product = secondMain.product
                secondConditioner.product = thirdMain.product

                val firstConditionerIdx = value.indexOf(firstMain)
                val secondConditionerIdx = value.indexOf(thirdMain)

                before = value.subList(0, firstConditionerIdx)
                after = value.subList(secondConditionerIdx + 1, value.size)
            }
    }

    data class CG(
        override var date: LocalDate = LocalDate.now(),
        private val firstConditioner: CareProduct = CareProduct(ProductApplication.Type.CONDITIONER),
        private val secondConditioner: CareProduct = CareProduct(ProductApplication.Type.CONDITIONER),
        private var before: List<CareProduct> = listOf(),
        private var after: List<CareProduct> = listOf()
    ) : Care() {

        override var steps: List<CareProduct>
            get() = mutableListOf<CareProduct>().apply {
                addAll(before)
                add(firstConditioner)
                add(secondConditioner)
                addAll(after)
            }
            set(value) {
                val mainProducts = value.filter { it.specificApplicationType != null }
                check(mainProducts.size == 2)

                val firstMain = mainProducts[0]
                val secondMain = mainProducts[1]

                check(firstMain.specificApplicationType == ProductApplication.Type.CONDITIONER)
                check(secondMain.specificApplicationType == ProductApplication.Type.CONDITIONER)

                firstConditioner.product = firstMain.product
                secondConditioner.product = secondMain.product

                val firstConditionerIdx = value.indexOf(firstMain)
                val secondConditionerIdx = value.indexOf(secondMain)

                before = value.subList(0, firstConditionerIdx)
                after = value.subList(secondConditionerIdx + 1, value.size)
            }
    }

    data class Custom(
        override var date: LocalDate = LocalDate.now(),
        override var steps: List<CareProduct> = listOf()
    ) : Care()
}



