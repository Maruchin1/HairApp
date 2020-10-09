package com.example.hairapp.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.tabs.KTabLayout
import com.agoda.kakao.text.KTextView
import com.example.hairapp.R
import org.hamcrest.Matcher

class HomeScreen : Screen<HomeScreen>() {

    val tabs = KTabLayout { withId(R.id.tabs) }

    val btnAdd = KView { withId(R.id.btn_add) }

    val productsRecycler = KRecyclerView(
        builder = { withId(R.id.recycler_products) },
        itemTypeBuilder = { itemType(::ProductItem) }
    )
}

class ProductItem(parent: Matcher<View>) : KRecyclerItem<ProductItem>(parent) {

    val card = KView { withId(R.id.item_product_card) }

    val image = KImageView { withId(R.id.item_product_image) }

    val name = KTextView { withId(R.id.item_product_name) }

    val manufacturer = KTextView { withId(R.id.item_product_manufacturer) }
}