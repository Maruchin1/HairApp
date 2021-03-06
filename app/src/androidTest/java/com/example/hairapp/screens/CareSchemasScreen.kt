package com.example.hairapp.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.example.hairapp.R
import org.hamcrest.Matcher

class CareSchemasScreen : Screen<CareSchemasScreen>() {

    val careSchemasRecycler = KRecyclerView(
        builder = { withId(R.id.care_schemas_recycler) },
        itemTypeBuilder = { itemType(::CareSchemaItem) }
    )

    val btnAddCareSchema = KButton { withId(R.id.btn_add_care_schema) }

}

class CareSchemaItem(parent: Matcher<View>) : KRecyclerItem<CareSchemaItem>(parent) {

    val card = KView(parent) { withId(R.id.item_care_schema_card) }

    val name = KTextView(parent) { withId(R.id.item_care_schema_name) }

}

class AddCareSchemaDialog : Screen<AddCareSchemaDialog>() {

    val input = KEditText { withId(R.id.input) }

    val btnClose = KButton { withText("Zamknij") }

    val btnSave = KButton { withText("Zapisz") }

}