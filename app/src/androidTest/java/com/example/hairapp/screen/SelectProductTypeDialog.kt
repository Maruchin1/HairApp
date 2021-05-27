package com.example.hairapp.screen

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.edit_care_schema.R

object SelectProductTypeDialog : Screen<SelectProductTypeDialog>() {

    val conditioner = KTextView { withText(R.string.conditioner) }

    val shampoo = KTextView { withText(R.string.shampoo) }

    val oil = KTextView { withText(R.string.oil) }

    val emulsifier = KTextView { withText(R.string.emulsifier) }

    val stylizer = KTextView { withText(R.string.stylizer) }

    val other = KTextView { withText(R.string.other) }
}