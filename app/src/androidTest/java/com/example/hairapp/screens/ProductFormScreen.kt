package com.example.hairapp.screens

import com.agoda.kakao.chipgroup.KChipGroup
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.scroll.KScrollView
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.example.hairapp.R

class ProductFormScreen : Screen<ProductFormScreen>() {

    val btnSave = KButton { withId(R.id.toolbar_action_btn) }

    val toolbarTitle = KTextView { withId(R.id.toolbar_title) }

    val fieldName = KEditText { withId(R.id.etx_product_name) }

    val fieldManufacturer = KEditText { withId(R.id.etx_manufacturer) }

    val chipGroupType = KChipGroup { withId(R.id.chip_group_type) }

    val chipGroupApplication = KChipGroup { withId(R.id.chip_group) }

    val scrollableContent = KScrollView { withId(R.id.content) }
}