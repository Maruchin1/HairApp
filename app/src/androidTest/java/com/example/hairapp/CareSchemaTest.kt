package com.example.hairapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hairapp.page_home.MainActivity
import com.example.hairapp.screens.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CareSchemaTest {

    companion object {
        const val OMO = "OMO"
        const val CG = "CG"
        const val CUSTOM_SCHEMA = "Wlasny schemat"
        const val CONDITIONER = "Odżywka"
        const val SHAMPOO = "Szampon"
        const val OIL = "Olej"
        const val EMULSIFIER = "Emulgator"
        const val STYLIZER = "Stylizator"
        const val OTHER = "Inne"
    }

    private val homeScreen by lazy { HomeScreen() }
    private val drawerScreen by lazy { DrawerScreen() }
    private val careSchemasScreen by lazy { CareSchemasScreen() }
    private val addCareSchemaDialog by lazy { AddCareSchemaDialog() }
    private val editCareSchemaScreen by lazy { EditCareSchemaScreen() }
    private val selectCareStepDialog by lazy { SelectCareStepDialog() }

    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private fun openCareSchemas() {
        homeScreen {
            btnDrawer.click()
        }
        drawerScreen {
            optionCareSchema.click()
        }
    }

    private fun addCustomSchema() {
        careSchemasScreen {
            btnAddCareSchema.click()
        }
        addCareSchemaDialog {
            input.typeText(CUSTOM_SCHEMA)
            btnSave.click()
        }
    }

    @Test
    fun displayDefaultSchemas() {
        openCareSchemas()
        careSchemasScreen {
            careSchemasRecycler {
                hasSize(2)
                firstChild<CareSchemaItem> {
                    name.hasText(OMO)
                }
                childAt<CareSchemaItem>(1) {
                    name.hasText(CG)
                }
            }
        }
    }

    @Test
    fun addEmptyCustomSchema() {
        openCareSchemas()
        addCustomSchema()
        editCareSchemaScreen {
            btnBack.click()
        }
        careSchemasScreen {
            careSchemasRecycler {
                hasSize(3)
                childAt<CareSchemaItem>(2) {
                    name.hasText(CUSTOM_SCHEMA)
                }
            }
        }
    }

    @Test
    fun addCustomSchemaWithSteps() {
        openCareSchemas()
        careSchemasScreen {
            btnAddCareSchema.click()
        }
        addCareSchemaDialog {
            input.typeText(CUSTOM_SCHEMA)
            btnSave.click()
        }
        editCareSchemaScreen {
            noStepsInfo.isDisplayed()
            recyclerCareSchemaSteps {
                hasSize(0)
            }
            btnAddStep.click()
        }
        selectCareStepDialog {
            optionConditioner.click()
        }
        editCareSchemaScreen {
            noStepsInfo.isNotDisplayed()
            recyclerCareSchemaSteps {
                hasSize(1)
                firstChild<CareSchemaStepItem> {
                    name.hasText(CONDITIONER)
                }
            }
            btnAddStep.click()
        }
        selectCareStepDialog {
            optionShampoo.click()
        }
        editCareSchemaScreen {
            recyclerCareSchemaSteps {
                hasSize(2)
                childAt<CareSchemaStepItem>(1) {
                    name.hasText(SHAMPOO)
                }
            }
            btnAddStep.click()
        }
        selectCareStepDialog {
            optionOil.click()
        }
        editCareSchemaScreen {
            recyclerCareSchemaSteps {
                hasSize(3)
                childAt<CareSchemaStepItem>(2) {
                    name.hasText(OIL)
                }
            }
            btnAddStep.click()
        }
        selectCareStepDialog {
            optionEmulsifier.click()
        }
        editCareSchemaScreen {
            recyclerCareSchemaSteps {
                hasSize(4)
                childAt<CareSchemaStepItem>(3) {
                    name.hasText(EMULSIFIER)
                }
            }
            btnAddStep.click()
        }
        selectCareStepDialog {
            optionStylizer.click()
        }
        editCareSchemaScreen {
            recyclerCareSchemaSteps {
                hasSize(5)
                childAt<CareSchemaStepItem>(4) {
                    name.hasText(STYLIZER)
                }
            }
            btnAddStep.click()
        }
        selectCareStepDialog {
            optionOther.click()
        }
        editCareSchemaScreen {
            recyclerCareSchemaSteps {
                hasSize(6)
                childAt<CareSchemaStepItem>(5) {
                    name.hasText(OTHER)
                }
            }
            btnBack.click()
        }
        careSchemasScreen {
            careSchemasRecycler {
                hasSize(3)
                childAt<CareSchemaItem>(2) {
                    name.hasText(CUSTOM_SCHEMA)
                }
            }
        }
    }

}