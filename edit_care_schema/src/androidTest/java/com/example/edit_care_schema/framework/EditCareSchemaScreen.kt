package com.example.edit_care_schema.framework

import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.toolbar.KToolbar
import com.example.edit_care_schema.R

class EditCareSchemaScreen : Screen<EditCareSchemaScreen>() {

    val toolbar = KToolbar { withId(R.id.toolbar) }

    val changeNameButton = KButton { withId(R.id.action_change_schema_name) }

    val deleteSchemaButton = KButton { withId(R.id.action_delete_schema) }

    val dialogInput = KEditText { withId(R.id.input) }

    val stepsRecycler = KRecyclerView(
        builder = { withId(R.id.steps_recycler) },
        itemTypeBuilder = { itemType(::KCareSchemaStepItem) }
    )

    val addStepButton = KButton { withId(R.id.fab_add_step) }
}