package com.example.hairapp.screen

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.example.hairapp.R

object HomeScreen : Screen<HomeScreen>() {

    val productsListButton = KButton { withId(R.id.productsListFragment) }

    val careSchemasListButton = KButton { withId(R.id.careSchemasListFragment) }
}