package com.example.hairapp.product_form

import androidx.test.espresso.Espresso
import com.agoda.kakao.common.utilities.getResourceString
import com.example.hairapp.R
import com.example.hairapp.screen.KProductItem
import org.junit.Test

class SuccessfullyAddNewProduct : ProductFormTest() {

    @Test
    fun typeProductName() {
        productFormScreen {
            productNameInput {
                scrollTo()
                typeText("Produkt testowy")
                hasText("Produkt testowy")
            }
        }
    }

    @Test
    fun typeManufacturer() {
        productFormScreen {
            manufacturerInput {
                scrollTo()
                typeText("Producent testowy")
                hasText("Producent testowy")
            }
        }
    }

    @Test
    fun selectCompositionOfIngredients() {
        productFormScreen {
            compositionOfIngredients {
                selectChip(getResourceString(R.string.proteins))
                selectChip(getResourceString(R.string.humectants))

                isChipSelected(getResourceString(R.string.proteins))
                isChipSelected(getResourceString(R.string.humectants))
            }
        }
    }

    @Test
    fun selectProductApplications() {
        productFormScreen {
            applications {
                selectChip(getResourceString(R.string.foam))
                selectChip(getResourceString(R.string.gel))

                isChipSelected(getResourceString(R.string.foam))
                isChipSelected(getResourceString(R.string.gel))
            }
        }
    }

    @Test
    fun saveProductWithOnlyName() {
        productFormScreen {
            productNameInput {
                typeText("Produkt testowy")
            }
            Espresso.closeSoftKeyboard()
            saveButton {
                click()
            }
        }
        listScreen {
            toolbar {
                isDisplayed()
                hasTitle(getResourceString(R.string.products))
            }
            productsRecycler {
                hasSize(2)
                childAt<KProductItem>(0) {
                    productName.hasText("Produkt testowy")
                    manufacturer.hasText("")
                }
            }
        }
    }

    @Test
    fun saveProductWithAllData() {
        productFormScreen {
            productNameInput {
                typeText("Produkt testowy")
            }
            Espresso.closeSoftKeyboard()
            manufacturerInput {
                typeText("Producent testowy")
            }
            Espresso.closeSoftKeyboard()
            compositionOfIngredients {
                scrollTo()
                selectChip(getResourceString(R.string.proteins))
                selectChip(getResourceString(R.string.humectants))
            }
            applications {
                scrollTo()
                selectChip(getResourceString(R.string.foam))
                selectChip(getResourceString(R.string.gel))
            }
            saveButton {
                click()
            }
        }
        listScreen {
            toolbar {
                isDisplayed()
                hasTitle(getResourceString(R.string.products))
            }
            productsRecycler {
                hasSize(2)
                childAt<KProductItem>(0) {
                    productName.hasText("Produkt testowy")
                    manufacturer.hasText("Producent testowy")
                }
            }
        }
    }
}