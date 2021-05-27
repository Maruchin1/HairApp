package com.example.hairapp.product_form

import com.agoda.kakao.common.utilities.getResourceString
import com.example.hairapp.R
import org.junit.Test

class OpenNewProductFormTest : ProductFormTest() {

    @Test
    fun displayNewProductTitle() {
        productFormScreen {
            toolbar.hasTitle(R.string.new_product)
        }
    }

    @Test
    fun displayEmptyInputs() {
        productFormScreen {
            productNameInput.hasText("")

            manufacturerInput.hasText("")
        }
    }

    @Test
    fun displayCompositionOfIngredientsOptions() {
        productFormScreen {
            compositionOfIngredients {
                scrollTo()
                hasSize(3)
                hasChip(getResourceString(R.string.proteins))
                hasChip(getResourceString(R.string.emollients))
                hasChip(getResourceString(R.string.humectants))
            }
        }
    }

    @Test
    fun displayApplicationsOptions() {
        productFormScreen {
            applications {
                scrollTo()
                hasSize(12)
                hasChip(getResourceString(R.string.mild_shampoo))
                hasChip(getResourceString(R.string.medium_shampoo))
                hasChip(getResourceString(R.string.strong_shampoo))
                hasChip(getResourceString(R.string.conditioner))
                hasChip(getResourceString(R.string.cream))
                hasChip(getResourceString(R.string.mask))
                hasChip(getResourceString(R.string.leave_in_conditioner))
                hasChip(getResourceString(R.string.oil))
                hasChip(getResourceString(R.string.foam))
                hasChip(getResourceString(R.string.serum))
                hasChip(getResourceString(R.string.gel))
                hasChip(getResourceString(R.string.other))
            }
        }
    }
}