package com.example.care_schema_details.components

import com.example.care_schema_details.R
import com.example.common.modals.ActionsModal

internal enum class CareSchemaActions : ActionsModal.Action {
    CHANGE_SCHEMA_NAME {
        override val iconResId: Int = R.drawable.ic_round_title_24
        override val nameResId: Int = R.string.change_care_schema_name
    },
    CHANGE_STEPS_ORDER {
        override val iconResId: Int = R.drawable.ic_round_swap_vert_24
        override val nameResId: Int = R.string.change_steps_order
    },
    ADD_STEP {
        override val iconResId: Int = R.drawable.ic_round_add_24
        override val nameResId: Int = R.string.add_care_step
    },
    DELETE_SCHEMA {
        override val iconResId: Int = R.drawable.ic_round_delete_24
        override val nameResId: Int = R.string.delete_care_schema
    }
}