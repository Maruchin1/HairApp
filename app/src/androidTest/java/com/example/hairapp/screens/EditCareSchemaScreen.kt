package com.example.hairapp.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.example.hairapp.R
import org.hamcrest.Matcher

class EditCareSchemaScreen : Screen<EditCareSchemaScreen>() {

    val btnBack = KButton { withContentDescription("Toolbar navigation") }

    val recyclerCareSchemaSteps = KRecyclerView(
        builder = { withId(R.id.care_schema_steps_recycler) },
        itemTypeBuilder = { itemType(::CareSchemaStepItem) }
    )

    val noStepsInfo = KTextView { withText("Zdefiniuj schemat kroków dla swojej pielęgnacji") }

    val btnAddStep = KButton { withId(R.id.btn_add_step) }

}

class CareSchemaStepItem(parent: Matcher<View>) : KRecyclerItem<CareSchemaStepItem>(parent) {

    val card = KView(parent) { withId(R.id.item_care_schema_step_card) }

    val name = KTextView(parent) { withId(R.id.step_name) }

}

class SelectCareStepDialog : Screen<SelectCareStepDialog>() {

    val optionConditioner = KButton { withText("Odżywka") }

    val optionShampoo = KButton { withText("Szampon") }

    val optionOil = KButton { withText("Olej") }

    val optionEmulsifier = KButton { withText("Emulgator") }

    val optionStylizer = KButton { withText("Stylizator") }

    val optionOther = KButton { withText("Inne") }

}