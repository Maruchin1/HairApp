package com.example.hairapp.screens

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.example.hairapp.R

class DrawerScreen : Screen<DrawerScreen>() {

    val optionPhotosGallery = KView { withId(R.id.option_photos_gallery) }

    val optionCareSchema = KView { withId(R.id.option_care_schema) }

}