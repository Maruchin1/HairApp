package com.example.hairapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.dialog.KAlertDialog
import com.example.hairapp.screens.HomeScreen
import com.example.hairapp.screens.ProductFormScreen
import com.example.hairapp.screens.ProductItem
import com.example.hairapp.screens.ProductScreen
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductTest {

    companion object {
        const val PRODUCT_NAME = "Shauma"
        const val MANUFACTURER = "Kret sp. z.o.o."

        const val UPDATED_PRODUCT_NAME = "Nivea Men"
        const val UPDATED_MANUFACTURER = "Nivea"

        const val HUMECTANTS = "Humektanty"
        const val EMOLLIENTS = "Emolienty"
        const val PROTEINS = "Proteiny"

        const val CONDITIONER = "Odżywka"
        const val OIL = "Olej"
        const val SHAMPOO = "Średni szampon"
    }

    private val homeScreen by lazy { HomeScreen() }
    private val productFormScreen by lazy { ProductFormScreen() }
    private val productScreen by lazy { ProductScreen() }
    private val alertDialog by lazy { KAlertDialog() }

    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun saveNewProduct() = runBlocking {
        homeScreen {
            tabs.selectTab(1)
            btnAdd.click()
        }
        productFormScreen {
            fieldName.typeText(PRODUCT_NAME)
            Espresso.closeSoftKeyboard()

            fieldManufacturer.typeText(MANUFACTURER)
            Espresso.closeSoftKeyboard()

            chipGroupType {
                scrollTo()
                selectChip(HUMECTANTS)
                selectChip(PROTEINS)
            }

            chipGroupApplication {
                scrollTo()
                selectChip(CONDITIONER)
                selectChip(OIL)
            }

            scrollableContent.act {
                ViewActions.swipeDown()
            }

            btnSave.click()
        }
        homeScreen {
            productsRecycler {
                hasSize(1)

                firstChild<ProductItem> {
                    name.hasText(PRODUCT_NAME)
                    manufacturer.hasText(MANUFACTURER)
                    click()
                }
            }
        }
        productScreen {
            productName.hasText(PRODUCT_NAME)
            productManufacturer.hasText(MANUFACTURER)

            chipGroupType {
                scrollTo()
                hasChip(HUMECTANTS)
                hasChip(PROTEINS)
            }

            chipGroupApplication {
                scrollTo()
                hasChip(CONDITIONER)
                hasChip(OIL)
            }
        }
    }

    @Test
    fun updateProductData() {
        homeScreen {
            tabs.selectTab(1)
            btnAdd.click()
        }
        productFormScreen {
            fieldName.typeText(PRODUCT_NAME)
            Espresso.closeSoftKeyboard()

            fieldManufacturer.typeText(MANUFACTURER)
            Espresso.closeSoftKeyboard()

            chipGroupType {
                scrollTo()
                selectChip(HUMECTANTS)
                selectChip(PROTEINS)
            }

            chipGroupApplication {
                scrollTo()
                selectChip(CONDITIONER)
                selectChip(OIL)
            }

            scrollableContent.act {
                ViewActions.swipeDown()
            }

            btnSave.click()
        }
        homeScreen {
            productsRecycler {
                firstChild<ProductItem> {
                    click()
                }
            }
        }
        productScreen {
            btnEdit.click()
        }
        productFormScreen {
            fieldName {
                clearText()
                typeText(UPDATED_PRODUCT_NAME)
            }
            Espresso.closeSoftKeyboard()

            fieldManufacturer {
                clearText()
                typeText(UPDATED_MANUFACTURER)
            }
            Espresso.closeSoftKeyboard()

            chipGroupType {
                scrollTo()
                // Old
                selectChip(HUMECTANTS)
                selectChip(PROTEINS)
                // New
                selectChip(EMOLLIENTS)
            }

            chipGroupApplication {
                scrollTo()
                // Old
                selectChip(CONDITIONER)
                selectChip(OIL)
                // New
                selectChip(SHAMPOO)
            }

            scrollableContent.act {
                ViewActions.swipeDown()
            }

            btnSave.click()
        }
        productScreen {
            productName.hasText(UPDATED_PRODUCT_NAME)
            productManufacturer.hasText(UPDATED_MANUFACTURER)

            chipGroupType {
                scrollTo()
                hasChip(EMOLLIENTS)
            }

            chipGroupApplication {
                scrollTo()
                hasChip(SHAMPOO)
            }
        }
    }

    @Test
    fun deleteProduct() {
        homeScreen {
            tabs.selectTab(1)
            btnAdd.click()
        }
        productFormScreen {
            fieldName.typeText(PRODUCT_NAME)
            Espresso.closeSoftKeyboard()

            fieldManufacturer.typeText(MANUFACTURER)
            Espresso.closeSoftKeyboard()

            chipGroupType {
                scrollTo()
                selectChip(HUMECTANTS)
                selectChip(PROTEINS)
            }

            chipGroupApplication {
                scrollTo()
                selectChip(CONDITIONER)
                selectChip(OIL)
            }

            scrollableContent.act {
                ViewActions.swipeDown()
            }

            btnSave.click()
        }
        homeScreen {
            productsRecycler {
                firstChild<ProductItem> {
                    click()
                }
            }
        }
        productScreen {
            btnDelete.click()
        }
        alertDialog {
            title.hasText(R.string.confirm_delete)
            message.hasText(R.string.product_activity_confirm_delete_message)
            positiveButton.click()
        }
        homeScreen {
            productsRecycler {
                hasSize(0)
            }
        }
    }
}