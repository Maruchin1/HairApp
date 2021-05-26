package com.example.e2e_test

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.agoda.kakao.toolbar.KToolbar

class KProductsListScreen : Screen<KProductsListScreen>() {

    val toolbar = KToolbar { withId(R.id.toolbar) }

    val noProducts = KView { withId(R.id.no_products) }

    val noProductsIcon = KImageView {
        withId(R.id.icon)
        withParent { withId(R.id.no_products) }
    }

    val noProductMessage = KTextView {
        withId(R.id.message)
        withParent { withId(R.id.no_products) }
    }

    val productsRecycler = KRecyclerView(
        builder = { withId(R.id.products_recycler) },
        itemTypeBuilder = { itemType { KProductItem(it) } }
    )
}