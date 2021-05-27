package com.example.hairapp.care_schemas_list

import com.example.hairapp.R
import com.example.hairapp.screen.CareSchemasListScreen
import com.example.hairapp.screen.KCareSchemaItem
import org.junit.Test

class DisplayCareSchemasTest : CareSchemasListTest() {

    @Test
    fun displaySchemasTitle() {
        CareSchemasListScreen {
            toolbar.hasTitle(R.string.schemas)
        }
    }

    @Test
    fun displayDefaultSchemas() {
        CareSchemasListScreen {
            schemasRecycler {
                hasSize(2)
                childAt<KCareSchemaItem>(0) {
                    schemaName.hasText(R.string.cg)
                }
                childAt<KCareSchemaItem>(1) {
                    schemaName.hasText(R.string.omo)
                }
            }
        }
    }
}