package com.example.hairapp.screen

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.text.KTextView
import com.example.hairapp.R
import org.hamcrest.Matcher

class KCareSchemaItem(parent: Matcher<View>) : KRecyclerItem<KCareSchemaItem>(parent) {

    val card = KView(parent) { withId(R.id.card) }

    val schemaName = KTextView(parent) { withId(R.id.schema_name) }

    val stepsList = KView(parent) { withId(R.id.steps_list) }

    val noStepsInSchema = KView(parent) { withId(R.id.no_steps_in_schema) }

    val noStepsInSchemaMessage = KTextView(parent) {
        withId(R.id.message)
        withParent { withId(R.id.no_steps_in_schema) }
    }
}