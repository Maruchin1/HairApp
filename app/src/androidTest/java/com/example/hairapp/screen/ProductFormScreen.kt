package com.example.hairapp.screen

import com.agoda.kakao.chipgroup.KChipGroup
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.toolbar.KToolbar
import com.example.hairapp.R

object ProductFormScreen : Screen<ProductFormScreen>() {

    val toolbar = KToolbar { withId(R.id.toolbar) }

    val productPhotoImage = KImageView {
        withId(R.id.image)
        withParent { withId(R.id.product_photo) }
    }

    val productNameInput = KEditText { withId(R.id.product_name_input) }

    val manufacturerInput = KEditText { withId(R.id.manufacturer_input) }

    val compositionOfIngredients = KChipGroup {
        withId(R.id.chip_group)
        withParent { withId(R.id.composition_of_ingredients) }
    }

    val applications = KChipGroup {
        withId(R.id.chip_group)
        withParent { withId(R.id.product_applications) }
    }

    val saveButton = KButton { withId(R.id.save_button) }
}