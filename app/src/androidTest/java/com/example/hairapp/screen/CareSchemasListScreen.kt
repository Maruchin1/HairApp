package com.example.hairapp.screen

import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.toolbar.KToolbar
import com.example.hairapp.R

object CareSchemasListScreen : Screen<CareSchemasListScreen>() {

    val toolbar = KToolbar { withId(R.id.toolbar) }

    val addSchema = KButton { withId(R.id.action_add_schema) }

    val sortSchemas = KButton { withId(R.id.action_sort_schemas) }

    val schemasRecycler = KRecyclerView(
        builder = { withId(R.id.schemas_recycler) },
        itemTypeBuilder = { itemType { KCareSchemaItem(it) } }
    )
}