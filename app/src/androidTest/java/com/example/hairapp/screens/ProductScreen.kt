package com.example.hairapp.screens

import com.agoda.kakao.chipgroup.KChipGroup
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.example.hairapp.R

class ProductScreen : Screen<ProductScreen>() {

    val productName = KTextView { withId(R.id.txv_product_name) }

    val productManufacturer = KTextView { withId(R.id.txv_product_manufacturer) }

    val btnEdit = KButton { withId(R.id.btn_edit) }

    val btnDelete = KButton{withId(R.id.btn_delete)}

    val chipGroupType = KChipGroup { withId(R.id.chip_group_type) }

    val chipGroupApplication = KChipGroup { withId(R.id.chip_group) }
}