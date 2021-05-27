package com.example.hairapp.screen

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.text.KTextView
import com.example.edit_care_schema.R
import org.hamcrest.Matcher

class KCareSchemaStepItem(parent: Matcher<View>) : KRecyclerItem<KCareSchemaStepItem>(parent) {

    val stepNumber = KTextView(parent) { withId(R.id.step_number) }

    val stepName = KTextView(parent) { withId(R.id.step_name) }

    val dragHandle = KTextView(parent) { withId(R.id.drag_handle) }
}